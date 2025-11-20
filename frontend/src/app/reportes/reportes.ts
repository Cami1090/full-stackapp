import { Component, OnInit } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ReservaService } from '../services/reserva.service';
import { RecursoService } from '../services/recurso.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule, NgClass],
  templateUrl: './reportes.html',
  styleUrls: ['./reportes.css']
})
export class ReportesComponent implements OnInit {

  // Filtros
  fechaInicio = '';
  fechaFin = '';
  tipoReporte = 'reservas';

  // Datos
  reservas: any[] = [];
  recursos: any[] = [];
  usuarios: any[] = [];
  
  // Reportes
  reporteReservas: any[] = [];
  reporteRecursos: any[] = [];
  reporteUsuarios: any[] = [];

  isLoading = false;
  errorMsg = '';

  constructor(
    private reservaService: ReservaService,
    private recursoService: RecursoService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
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

    this.establecerFechasPorDefecto();
    this.cargarDatos();
  }

  establecerFechasPorDefecto() {
    const hoy = new Date();
    const primerDiaMes = new Date(hoy.getFullYear(), hoy.getMonth(), 1);
    
    this.fechaFin = hoy.toISOString().split('T')[0];
    this.fechaInicio = primerDiaMes.toISOString().split('T')[0];
  }

  cargarDatos() {
    this.isLoading = true;
    this.errorMsg = '';

    Promise.all([
      this.reservaService.getAllReservas().toPromise(),
      this.recursoService.getAllRecursos().toPromise(),
      this.authService.getAllUsuarios().toPromise()
    ]).then(([reservas, recursos, usuarios]: any) => {
      this.reservas = reservas || [];
      this.recursos = recursos || [];
      this.usuarios = usuarios || [];
      
      this.generarReportes();
      this.isLoading = false;
    }).catch((error) => {
      console.error('Error al cargar datos:', error);
      this.errorMsg = 'Error al cargar los datos para los reportes';
      this.isLoading = false;
    });
  }

  generarReportes() {
    switch(this.tipoReporte) {
      case 'reservas':
        this.generarReporteReservas();
        break;
      case 'recursos':
        this.generarReporteRecursos();
        break;
      case 'usuarios':
        this.generarReporteUsuarios();
        break;
    }
  }

  generarReporteReservas() {
    let reservasFiltradas = [...this.reservas];

    if (this.fechaInicio) {
      const fechaInicioDate = new Date(this.fechaInicio);
      reservasFiltradas = reservasFiltradas.filter(r => {
        const fechaReserva = new Date(r.fechaInicio);
        return fechaReserva >= fechaInicioDate;
      });
    }

    if (this.fechaFin) {
      const fechaFinDate = new Date(this.fechaFin);
      fechaFinDate.setHours(23, 59, 59, 999);
      reservasFiltradas = reservasFiltradas.filter(r => {
        const fechaReserva = new Date(r.fechaInicio);
        return fechaReserva <= fechaFinDate;
      });
    }

    const porEstado = reservasFiltradas.reduce((acc: any, reserva: any) => {
      const estado = reserva.estado || 'SIN_ESTADO';
      if (!acc[estado]) {
        acc[estado] = { estado, cantidad: 0, reservas: [] };
      }
      acc[estado].cantidad++;
      acc[estado].reservas.push(reserva);
      return acc;
    }, {});

    this.reporteReservas = Object.values(porEstado).map((item: any) => ({
      id: item.estado,
      tipo: 'Reservas',
      fecha: this.formatearRangoFechas(),
      detalle: `${item.cantidad} reservas con estado "${this.getEstadoTexto(item.estado)}"`,
      cantidad: item.cantidad,
      estado: item.estado
    }));
  }

  generarReporteRecursos() {
    const reservasPorRecurso = this.reservas.reduce((acc: any, reserva: any) => {
      const recursoId = reserva.recurso;
      if (!acc[recursoId]) {
        acc[recursoId] = { recursoId, cantidad: 0 };
      }
      acc[recursoId].cantidad++;
      return acc;
    }, {});

    this.reporteRecursos = Object.entries(reservasPorRecurso)
      .map(([recursoId, data]: [string, any]) => {
        const recurso = this.recursos.find(r => r.id === recursoId);
        return {
          id: recursoId,
          tipo: 'Recursos',
          fecha: this.formatearRangoFechas(),
          detalle: `Recurso "${recurso?.nombre || 'No encontrado'}" tiene ${data.cantidad} reservas`,
          cantidad: data.cantidad,
          nombreRecurso: recurso?.nombre || 'No encontrado'
        };
      })
      .sort((a: any, b: any) => b.cantidad - a.cantidad)
      .slice(0, 10);
  }

  generarReporteUsuarios() {
    const reservasPorUsuario = this.reservas.reduce((acc: any, reserva: any) => {
      const usuarioId = reserva.usuario;
      if (!acc[usuarioId]) {
        acc[usuarioId] = { usuarioId, cantidad: 0 };
      }
      acc[usuarioId].cantidad++;
      return acc;
    }, {});

    this.reporteUsuarios = Object.entries(reservasPorUsuario)
      .map(([usuarioId, data]: [string, any]) => {
        const usuario = this.usuarios.find((u: any) => u.id === usuarioId);
        return {
          id: usuarioId,
          tipo: 'Usuarios',
          fecha: this.formatearRangoFechas(),
          detalle: `Usuario "${usuario ? `${usuario.nombre} ${usuario.apellido}` : 'No encontrado'}" tiene ${data.cantidad} reservas`,
          cantidad: data.cantidad,
          nombreUsuario: usuario ? `${usuario.nombre} ${usuario.apellido}` : 'No encontrado'
        };
      })
      .sort((a: any, b: any) => b.cantidad - a.cantidad)
      .slice(0, 10);
  }

  filtrar() {
    this.generarReportes();
  }

  formatearRangoFechas(): string {
    if (this.fechaInicio && this.fechaFin) {
      return `${this.formatearFecha(this.fechaInicio)} - ${this.formatearFecha(this.fechaFin)}`;
    }
    return 'Todas las fechas';
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

  getEstadoTexto(estado: string): string {
    switch(estado) {
      case 'CONFIRMADA': return 'Confirmada';
      case 'PENDIENTE': return 'Pendiente';
      case 'CANCELADA': return 'Cancelada';
      default: return estado;
    }
  }

  getReportesActuales(): any[] {
    switch(this.tipoReporte) {
      case 'reservas': return this.reporteReservas;
      case 'recursos': return this.reporteRecursos;
      case 'usuarios': return this.reporteUsuarios;
      default: return [];
    }
  }

  // 👉 Función añadida para reemplazar el reduce del HTML
  getTotalReservas(): number {
    return this.reporteReservas.reduce((sum, r) => sum + (r.cantidad || 0), 0);
  }
}
