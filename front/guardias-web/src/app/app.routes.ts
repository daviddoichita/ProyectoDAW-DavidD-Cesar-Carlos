import { Routes } from '@angular/router';
import { CuadranteProfesorComponent } from './components/cuadrante-profesor/cuadrante-profesor.component';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
  { path: 'cuadrante', component: CuadranteProfesorComponent },
  { path: 'login', component: LoginComponent },
];
