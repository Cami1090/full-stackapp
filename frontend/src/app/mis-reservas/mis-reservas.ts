import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ReservaService } from '../services/reserva.service';
import { RecursoService } from '../services/recurso.service';

@Component({
  selector: 'app-mis-reservas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mis-reservas.html',
  styleUrl: './mis-reservas.css'
})
export class MisReservasComponent implements OnInit {

  reservas: any[] = [];
  recursosMap: Map<string, any> = new Map();
  isLoading = false;
  errorMsg = '';
  successMsg = '';

  constructor(
    private reservaService: ReservaService,
    private recursoService: RecursoService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cargarReservas();
    this.cargarRecursos();
  }

  cargarRecursos() {
    this.recursoService.getAllRecursos().subscribe({
      next: (recursos) => {
        recursos.forEach(recurso => {
          this.recursosMap.set(recurso.id, recurso);
        });
      },
      error: (error) => {
        console.error('Error al cargar recursos:', error);
      }
    });
  }

  cargarReservas() {
    const userStr = localStorage.getItem('user');
    if (!userStr) {
      this.errorMsg = 'No se encontró información del usuario';
      return;
    }

    const user = JSON.parse(userStr);
    this.isLoading = true;

    this.reservaService.getReservasPorUsuario(user.id).subscribe({
      next: (reservas) => {
        // Filtrar solo reservas confirmadas (no canceladas)
        this.reservas = reservas.filter(r => r.estado === 'CONFIRMADA');
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error al cargar reservas:', error);
        this.errorMsg = 'Error al cargar tus reservas';
        this.isLoading = false;
      }
    });
  }

  getNombreRecurso(recursoId: string): string {
    const recurso = this.recursosMap.get(recursoId);
    return recurso ? recurso.nombre : 'Recurso no encontrado';
  }

  cancelarReserva(reserva: any) {
    if (!confirm('¿Estás seguro de que deseas cancelar esta reserva?')) {
      return;
    }

    this.isLoading = true;
    this.errorMsg = '';
    this.successMsg = '';

    const reservaActualizada = {
      ...reserva,
      estado: 'CANCELADA'
    };

    this.reservaService.actualizarReserva(reserva.id, reservaActualizada).subscribe({
      next: () => {
        this.successMsg = 'Reserva cancelada exitosamente';
        this.isLoading = false;
        // Recargar reservas
        this.cargarReservas();
        // Limpiar mensaje después de 3 segundos
        setTimeout(() => {
          this.successMsg = '';
        }, 3000);
        // Notificar al calendario que se actualice (mediante evento o recarga)
        // El calendario se actualizará cuando el usuario navegue a él
      },
      error: (error) => {
        console.error('Error al cancelar reserva:', error);
        this.errorMsg = 'Error al cancelar la reserva';
        this.isLoading = false;
      }
    });
  }

  formatearFecha(fecha: string): string {
    if (!fecha) return '';
    const date = new Date(fecha);
    return date.toLocaleDateString('es-ES', { 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric' 
    });
  }
}

