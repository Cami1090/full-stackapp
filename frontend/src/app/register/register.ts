import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent {

  fullName = '';
  email = '';
  password = '';
  confirmPassword = '';

  successMsg = '';
  errorMsg = '';
  isLoading = false;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  register() {
    this.errorMsg = '';
    this.successMsg = '';

    // Validaciones
    if (!this.fullName || !this.email || !this.password || !this.confirmPassword) {
      this.errorMsg = 'Completa todos los campos';
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.errorMsg = 'Las contraseñas no coinciden';
      return;
    }

    if (this.password.length < 6) {
      this.errorMsg = 'La contraseña debe tener al menos 6 caracteres';
      return;
    }

    // Separar nombre completo en nombre y apellido
    const nameParts = this.fullName.trim().split(' ');
    const nombre = nameParts[0] || '';
    const apellido = nameParts.slice(1).join(' ') || '';

    if (!nombre || !apellido) {
      this.errorMsg = 'Por favor ingresa nombre y apellido';
      return;
    }

    // Preparar datos para enviar al backend
    const usuarioData = {
      nombre: nombre,
      apellido: apellido,
      email: this.email,
      contraseña: this.password,
      rol: 'CLIENTE' // Rol por defecto para nuevos usuarios
    };

    this.isLoading = true;

    // Llamar al servicio para registrar el usuario
    this.authService.register(usuarioData).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMsg = 'Usuario registrado correctamente';
        this.clearForm();
        
        // Redirigir al login después de 2 segundos
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error al registrar usuario:', error);
        
        if (error.status === 409) {
          this.errorMsg = 'El email ya está registrado';
        } else if (error.status === 400) {
          this.errorMsg = 'Datos inválidos. Por favor verifica la información';
        } else if (error.error && error.error.message) {
          this.errorMsg = error.error.message;
        } else {
          this.errorMsg = 'Error al registrar usuario. Por favor intenta nuevamente';
        }
      }
    });
  }

  clearForm() {
    this.fullName = '';
    this.email = '';
    this.password = '';
    this.confirmPassword = '';
  }

  backToLogin() {
    this.router.navigate(['/login']);
  }
}


