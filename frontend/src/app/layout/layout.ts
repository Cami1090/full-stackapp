import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class LayoutComponent implements OnInit {

  username = 'Usuario';
  isAdmin = false;

  constructor(private router: Router) {}

  ngOnInit() {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      const user = JSON.parse(userStr);
      this.username = `${user.nombre} ${user.apellido}`;
      this.isAdmin = user.rol === 'ADMIN';
    }
  }

  logout() {
    localStorage.removeItem('logged');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }
}
