import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ReservaService } from '../services/reserva.service';
import { RecursoService } from '../services/recurso.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent implements OnInit {

  // Estadísticas
  totalReservas = 0;
  reservasConfirmadas = 0;
  reservasCanceladas = 0;
  totalUsuarios = 0;
  totalRecursos = 0;
  recursosDisponibles = 0;
  ingresosMes = 0;

  // Reservas recientes
  reservasRecientes: any[] = [];
  recursosMap: Map<string, any> = new Map();
  usuariosMap: Map<string, any> = new Map();

  isLoading = false;

  constructor(
    private reservaService: ReservaService,
    private recursoService: RecursoService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    // Verificar si es admin
    const userStr = localStorage.getItem('user');
    if (!userStr) {
      this.router.navigate(['/login']);
      return;
    }

    const user = JSON.parse(userStr);
    if (user.rol !== 'ADMIN') {
      this.router.navigate(['/catalogo']);
      return;
    }

    this.cargarDatos();
  }

  cargarDatos() {
    this.isLoading = true;
    this.cargarRecursos();
    this.cargarUsuarios();
    this.cargarReservas();
  }

  cargarRecursos() {
    this.recursoService.getAllRecursos().subscribe({
      next: (recursos) => {
        this.totalRecursos = recursos.length;
        this.recursosDisponibles = recursos.filter(r => r.estado === 'Disponible').length;
        
        // Mapear recursos para uso posterior
        recursos.forEach(recurso => {
          this.recursosMap.set(recurso.id, recurso);
        });
      },
      error: (error) => {
        console.error('Error al cargar recursos:', error);
      }
    });
  }

  cargarUsuarios() {
    this.authService.getAllUsuarios().subscribe({
      next: (usuarios: any) => {
        this.totalUsuarios = usuarios.length;
        
        // Mapear usuarios para uso posterior
        usuarios.forEach((usuario: any) => {
          this.usuariosMap.set(usuario.id, usuario);
        });
      },
      error: (error) => {
        console.error('Error al cargar usuarios:', error);
      }
    });
  }

  cargarReservas() {
    this.reservaService.getAllReservas().subscribe({
      next: (reservas) => {
        this.totalReservas = reservas.length;
        this.reservasConfirmadas = reservas.filter(r => r.estado === 'CONFIRMADA').length;
        this.reservasCanceladas = reservas.filter(r => r.estado === 'CANCELADA').length;

        // Calcular ingresos del mes (solo reservas confirmadas)
        const hoy = new Date();
        const inicioMes = new Date(hoy.getFullYear(), hoy.getMonth(), 1);
        
        this.ingresosMes = reservas
          .filter(r => {
            if (r.estado !== 'CONFIRMADA') return false;
            const fechaReserva = new Date(r.fechaInicio);
            return fechaReserva >= inicioMes;
          })
          .reduce((total, r) => {
            const precio = parseFloat(r.precio || '0');
            return total + precio;
          }, 0);

        // Obtener reservas recientes (últimas 5)
        this.reservasRecientes = reservas
          .sort((a, b) => {
            const fechaA = new Date(b.fechaInicio).getTime();
            const fechaB = new Date(a.fechaInicio).getTime();
            return fechaA - fechaB;
          })
          .slice(0, 5);

        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error al cargar reservas:', error);
        this.isLoading = false;
      }
    });
  }

  getNombreRecurso(recursoId: string): string {
    const recurso = this.recursosMap.get(recursoId);
    return recurso ? recurso.nombre : 'Recurso no encontrado';
  }

  getNombreUsuario(usuarioId: string): string {
    const usuario = this.usuariosMap.get(usuarioId);
    return usuario ? `${usuario.nombre} ${usuario.apellido}` : 'Usuario no encontrado';
  }

  formatearFecha(fecha: string): string {
    if (!fecha) return '';
    const date = new Date(fecha);
    return date.toLocaleDateString('es-ES', { 
      year: 'numeric', 
      month: '2-digit', 
      day: '2-digit' 
    });
  }

  formatearHora(fecha: string): string {
    if (!fecha) return '';
    const date = new Date(fecha);
    return date.toLocaleTimeString('es-ES', { 
      hour: '2-digit', 
      minute: '2-digit' 
    });
  }

  getEstadoBadgeClass(estado: string): string {
    switch(estado) {
      case 'CONFIRMADA':
        return 'success';
      case 'PENDIENTE':
        return 'pending';
      case 'CANCELADA':
        return 'cancelled';
      default:
        return 'pending';
    }
  }

  getEstadoTexto(estado: string): string {
    switch(estado) {
      case 'CONFIRMADA':
        return 'Confirmado';
      case 'PENDIENTE':
        return 'Pendiente';
      case 'CANCELADA':
        return 'Cancelado';
      default:
        return estado;
    }
  }
}
