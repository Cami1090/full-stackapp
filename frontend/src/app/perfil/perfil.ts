import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './perfil.html',
  styleUrl: './perfil.css'
})
export class PerfilComponent implements OnInit {

  usuario: any = null;
  
  // Datos del formulario
  nombre = '';
  apellido = '';
  email = '';
  password = '';
  confirmPassword = '';
  
  // Modo edición
  editando = false;
  
  // Mensajes
  successMsg = '';
  errorMsg = '';
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cargarUsuario();
  }

  cargarUsuario() {
    const userStr = localStorage.getItem('user');
    if (!userStr) {
      this.router.navigate(['/login']);
      return;
    }

    this.usuario = JSON.parse(userStr);
    this.nombre = this.usuario.nombre || '';
    this.apellido = this.usuario.apellido || '';
    this.email = this.usuario.email || '';
  }

  activarEdicion() {
    this.editando = true;
    this.errorMsg = '';
    this.successMsg = '';
    this.password = '';
    this.confirmPassword = '';
  }

  cancelarEdicion() {
    this.editando = false;
    this.cargarUsuario(); // Recargar datos originales
    this.errorMsg = '';
    this.successMsg = '';
  }

  actualizarPerfil() {
    this.errorMsg = '';
    this.successMsg = '';

    // Validaciones
    if (!this.nombre || !this.apellido || !this.email) {
      this.errorMsg = 'Completa todos los campos obligatorios';
      return;
    }

    // Si se cambió la contraseña, validar
    if (this.password || this.confirmPassword) {
      if (this.password.length < 6) {
        this.errorMsg = 'La contraseña debe tener al menos 6 caracteres';
        return;
      }
      if (this.password !== this.confirmPassword) {
        this.errorMsg = 'Las contraseñas no coinciden';
        return;
      }
    }

    this.isLoading = true;

    // Preparar datos para actualizar
    const datosActualizados: any = {
      id: this.usuario.id,
      nombre: this.nombre,
      apellido: this.apellido,
      email: this.email,
      rol: this.usuario.rol
    };

    // Solo incluir contraseña si se cambió
    if (this.password) {
      datosActualizados.contraseña = this.password;
    } else {
      // Mantener la contraseña actual (no se envía si no se cambia)
      // El backend debería mantener la contraseña existente si no se envía
    }

    // Actualizar en el backend
    this.authService.actualizarUsuario(this.usuario.id, datosActualizados).subscribe({
      next: (response) => {
        this.isLoading = false;
        
        // Actualizar localStorage
        const usuarioActualizado = {
          id: response.id,
          nombre: response.nombre,
          apellido: response.apellido,
          email: response.email,
          rol: response.rol
        };
        localStorage.setItem('user', JSON.stringify(usuarioActualizado));
        
        this.usuario = usuarioActualizado;
        this.successMsg = 'Perfil actualizado correctamente';
        this.editando = false;
        
        // Limpiar mensaje después de 3 segundos
        setTimeout(() => {
          this.successMsg = '';
        }, 3000);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error al actualizar perfil:', error);
        
        if (error.status === 400) {
          this.errorMsg = 'Datos inválidos. Por favor verifica la información';
        } else if (error.status === 409) {
          this.errorMsg = 'El email ya está en uso por otro usuario';
        } else {
          this.errorMsg = 'Error al actualizar el perfil. Por favor intenta nuevamente';
        }
      }
    });
  }

  getRolTexto(rol: string): string {
    switch(rol) {
      case 'ADMIN':
        return 'Administrador';
      case 'CLIENTE':
        return 'Cliente';
      default:
        return rol;
    }
  }
}
