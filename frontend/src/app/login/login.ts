import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  email = '';
  password = '';
  errorMsg = '';
  isLoading = false;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  login() {
    this.errorMsg = '';

    if (!this.email || !this.password) {
      this.errorMsg = 'Por favor completa todos los campos';
      return;
    }

    this.isLoading = true;

    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        this.isLoading = false;
        
        // Guardar información del usuario en localStorage
        localStorage.setItem('logged', 'true');
        localStorage.setItem('user', JSON.stringify({
          id: response.id,
          nombre: response.nombre,
          apellido: response.apellido,
          email: response.email,
          rol: response.rol
        }));
        
        // Redirigir al dashboard
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error al iniciar sesión:', error);
        
        if (error.status === 401) {
          this.errorMsg = 'Correo o contraseña incorrectos';
        } else {
          this.errorMsg = 'Error al iniciar sesión. Por favor intenta nuevamente';
        }
      }
    });
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}

