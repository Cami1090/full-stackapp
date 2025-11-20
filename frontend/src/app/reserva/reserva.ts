import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { RecursoService } from '../services/recurso.service';
import { ReservaService } from '../services/reserva.service';

@Component({
  selector: 'app-reserva',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './reserva.html',
  styleUrl: './reserva.css'
})
export class ReservaComponent implements OnInit {

  recursos: any[] = [];
  recursoSeleccionado = '';
  fechaInicio = '';
  fechaFin = '';
  asiento = '';
  precio = '';

  successMsg = '';
  errorMsg = '';
  isLoading = false;

  constructor(
    private recursoService: RecursoService,
    private reservaService: ReservaService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.cargarRecursos();
    
    // Si viene desde el catálogo con un recurso preseleccionado
    this.route.queryParams.subscribe(params => {
      if (params['recursoId']) {
        this.recursoSeleccionado = params['recursoId'];
      }
    });
  }

  cargarRecursos() {
    this.recursoService.getAllRecursos().subscribe({
      next: (recursos) => {
        // Solo mostrar recursos disponibles
        this.recursos = recursos.filter(r => r.estado === 'Disponible');
      },
      error: (error) => {
        console.error('Error al cargar recursos:', error);
        this.errorMsg = 'Error al cargar recursos disponibles';
      }
    });
  }

  crearReserva() {
    this.errorMsg = '';
    this.successMsg = '';

    // Validaciones
    if (!this.recursoSeleccionado || !this.fechaInicio || !this.fechaFin) {
      this.errorMsg = 'Completa todos los campos obligatorios';
      return;
    }

    const fechaInicioDate = new Date(this.fechaInicio);
    const fechaFinDate = new Date(this.fechaFin);
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);

    if (fechaInicioDate < hoy) {
      this.errorMsg = 'La fecha de inicio no puede ser anterior a hoy';
      return;
    }

    if (fechaFinDate < fechaInicioDate) {
      this.errorMsg = 'La fecha de fin debe ser posterior a la fecha de inicio';
      return;
    }

    // Obtener usuario actual
    const userStr = localStorage.getItem('user');
    if (!userStr) {
      this.errorMsg = 'No se encontró información del usuario. Por favor inicia sesión nuevamente';
      return;
    }

    const user = JSON.parse(userStr);

    const reservaData = {
      usuario: user.id,
      recurso: this.recursoSeleccionado,
      estado: 'CONFIRMADA',
      asiento: this.asiento || null,
      precio: this.precio || '0',
      fechaInicio: this.fechaInicio,
      fechaFin: this.fechaFin
    };

    this.isLoading = true;

    this.reservaService.crearReserva(reservaData).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMsg = 'Reserva creada exitosamente';
        
        // Redirigir al calendario después de 2 segundos
        setTimeout(() => {
          this.router.navigate(['/calendario']);
        }, 2000);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error al crear reserva:', error);
        
        if (error.status === 400) {
          this.errorMsg = 'Datos inválidos. Por favor verifica la información';
        } else {
          this.errorMsg = 'Error al crear la reserva. Por favor intenta nuevamente';
        }
      }
    });
  }

  cancelar() {
    this.router.navigate(['/catalogo']);
  }
}

