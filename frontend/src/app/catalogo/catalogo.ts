import { Component, OnInit } from '@angular/core';
import { NgFor, NgClass, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { RecursoService } from '../services/recurso.service';
import { ReservaService } from '../services/reserva.service';

@Component({
  selector: 'app-catalogo',
  standalone: true,
  imports: [NgFor, NgClass, NgIf],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.css',
})
export class CatalogoComponent implements OnInit {

  recursos: any[] = [];
  isLoading = false;

  constructor(
    private recursoService: RecursoService,
    private reservaService: ReservaService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cargarRecursos();
  }

  cargarRecursos() {
    this.isLoading = true;
    this.recursoService.getAllRecursos().subscribe({
      next: (recursos) => {
        console.log('Recursos cargados:', recursos);
        this.recursos = recursos;
        this.verificarDisponibilidad();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error al cargar recursos:', error);
        this.isLoading = false;
        this.recursos = []; // Asegurar que el array esté vacío en caso de error
      }
    });
  }

  verificarDisponibilidad() {
    // Cargar todas las reservas activas para verificar disponibilidad
    this.reservaService.getReservasPorEstado('CONFIRMADA').subscribe({
      next: (reservas) => {
        const recursosOcupados = new Set(reservas.map(r => r.recurso));
        this.recursos.forEach(recurso => {
          // Solo cambiar el estado si el recurso no está en mantenimiento
          if (recurso.estado !== 'Mantenimiento') {
            if (recursosOcupados.has(recurso.id)) {
              recurso.estado = 'Ocupado';
            } else {
              recurso.estado = 'Disponible';
            }
          }
        });
      },
      error: (error) => {
        console.error('Error al verificar disponibilidad:', error);
        // Si hay error, establecer todos como disponibles (excepto mantenimiento)
        this.recursos.forEach(recurso => {
          if (recurso.estado !== 'Mantenimiento') {
            recurso.estado = 'Disponible';
          }
        });
      }
    });
  }

  reservar(recurso: any) {
    if (recurso.estado === 'Disponible') {
      this.router.navigate(['/reserva'], { 
        queryParams: { recursoId: recurso.id, recursoNombre: recurso.nombre } 
      });
    }
  }
}
