import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';

import { LoginComponent } from './login/login';
import { LayoutComponent } from './layout/layout';
import { DashboardComponent } from './dashboard/dashboard';
import { CatalogoComponent } from './catalogo/catalogo';
import { CalendarioComponent } from './calendario/calendario';
import { PerfilComponent } from './perfil/perfil';
import { ReportesComponent } from './reportes/reportes';
import { RegisterComponent } from './register/register';
import { RecursoComponent } from './recurso/recurso';
import { ReservaComponent } from './reserva/reserva';
import { MisReservasComponent } from './mis-reservas/mis-reservas';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'catalogo', component: CatalogoComponent },
      { path: 'calendario', component: CalendarioComponent },
      { path: 'perfil', component: PerfilComponent },
      { path: 'reportes', component: ReportesComponent },
      { path: 'recurso', component: RecursoComponent },
      { path: 'reserva', component: ReservaComponent },
      { path: 'mis-reservas', component: MisReservasComponent },
    ]
  },

  { path: '**', redirectTo: 'login' }
];


