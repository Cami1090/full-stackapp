import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RecursoService } from '../services/recurso.service';

@Component({
  selector: 'app-recurso',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './recurso.html',
  styleUrl: './recurso.css'
})
export class RecursoComponent {

  nombre = '';
  tipo = '';
  capacidad = 0;
  ubicacion = '';
  estado = 'Disponible';

  successMsg = '';
  errorMsg = '';
  isLoading = false;

  tiposRecurso = ['Sala de Reuniones', 'Oficina', 'Auditorio', 'Laboratorio', 'Taller', 'Otro'];
  estadosRecurso = ['Disponible', 'Ocupado', 'Mantenimiento'];

  constructor(private recursoService: RecursoService) {}

  registrarRecurso() {
    this.errorMsg = '';
    this.successMsg = '';

    // Validaciones
    if (!this.nombre || !this.tipo || !this.ubicacion || this.capacidad <= 0) {
      this.errorMsg = 'Completa todos los campos correctamente';
      return;
    }

    const recursoData = {
      nombre: this.nombre,
      tipo: this.tipo,
      capacidad: this.capacidad,
      ubicacion: this.ubicacion,
      estado: this.estado
    };

    this.isLoading = true;

    this.recursoService.crearRecurso(recursoData).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMsg = 'Recurso registrado correctamente';
        this.limpiarFormulario();
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error al registrar recurso:', error);
        
        if (error.status === 400) {
          this.errorMsg = 'Datos inválidos. Por favor verifica la información';
        } else {
          this.errorMsg = 'Error al registrar recurso. Por favor intenta nuevamente';
        }
      }
    });
  }

  limpiarFormulario() {
    this.nombre = '';
    this.tipo = '';
    this.capacidad = 0;
    this.ubicacion = '';
    this.estado = 'Disponible';
  }
}

