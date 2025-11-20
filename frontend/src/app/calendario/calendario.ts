import { Component, OnInit, OnDestroy } from '@angular/core';
import { NgFor, NgIf, NgClass } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { ReservaService } from '../services/reserva.service';

@Component({
  selector: 'app-calendario',
  standalone: true,
  imports: [NgFor, NgIf, NgClass],
  templateUrl: './calendario.html',
  styleUrl: './calendario.css',
})
export class CalendarioComponent implements OnInit, OnDestroy {

  dayNames = ['Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb', 'Dom'];
  monthNames = [
    'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
    'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
  ];

  currentDate = new Date();
  currentMonth = this.currentDate.getMonth();
  currentYear = this.currentDate.getFullYear();

  calendarDays: number[] = [];
  diasConReserva: Set<number> = new Set();
  private routerSubscription?: Subscription;

  constructor(
    private reservaService: ReservaService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cargarReservas();
    
    // Recargar reservas cuando se navega a este componente
    this.routerSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        if (event.url === '/calendario' || event.urlAfterRedirects === '/calendario') {
          this.cargarReservas();
        }
      });
  }

  ngOnDestroy() {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

  cargarReservas() {
    this.reservaService.getAllReservas().subscribe({
      next: (reservas) => {
        // Solo considerar reservas confirmadas
        const reservasConfirmadas = reservas.filter(r => r.estado === 'CONFIRMADA');
        this.marcarDiasConReserva(reservasConfirmadas);
        this.generateCalendar();
      },
      error: (error) => {
        console.error('Error al cargar reservas:', error);
        this.generateCalendar();
      }
    });
  }

  marcarDiasConReserva(reservas: any[]) {
    this.diasConReserva.clear();
    
    reservas.forEach(reserva => {
      const fechaInicio = new Date(reserva.fechaInicio);
      const fechaFin = new Date(reserva.fechaFin);
      
      // Si la reserva está en el mes actual, marcar los días
      if (fechaInicio.getMonth() === this.currentMonth && 
          fechaInicio.getFullYear() === this.currentYear) {
        
        // Marcar todas las fechas en el rango de la reserva
        const fechaActual = new Date(fechaInicio);
        while (fechaActual <= fechaFin && 
               fechaActual.getMonth() === this.currentMonth &&
               fechaActual.getFullYear() === this.currentYear) {
          this.diasConReserva.add(fechaActual.getDate());
          fechaActual.setDate(fechaActual.getDate() + 1);
        }
      }
    });
  }

  tieneReserva(dia: number): boolean {
    return this.diasConReserva.has(dia);
  }

  generateCalendar() {
    this.calendarDays = [];

    // Día de inicio del mes (1 = lunes ... 7 = domingo)
    let firstDay = new Date(this.currentYear, this.currentMonth, 1).getDay();
    if (firstDay === 0) firstDay = 7;

    const daysInMonth = new Date(this.currentYear, this.currentMonth + 1, 0).getDate();

    // Espacios vacíos antes del día 1
    for (let i = 1; i < firstDay; i++) {
      this.calendarDays.push(0);
    }

    // Días del mes
    for (let i = 1; i <= daysInMonth; i++) {
      this.calendarDays.push(i);
    }
  }

  prevMonth() {
    this.currentMonth--;
    if (this.currentMonth < 0) {
      this.currentMonth = 11;
      this.currentYear--;
    }
    this.cargarReservas();
  }

  nextMonth() {
    this.currentMonth++;
    if (this.currentMonth > 11) {
      this.currentMonth = 0;
      this.currentYear++;
    }
    this.cargarReservas();
  }
}
