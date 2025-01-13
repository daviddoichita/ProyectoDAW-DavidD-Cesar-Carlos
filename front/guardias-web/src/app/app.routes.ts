import { RouterModule, Routes } from '@angular/router';
import { NuevaFaltaComponent } from './components/nueva-falta/nueva-falta.component';
import { CuadranteProfesorComponent } from './components/cuadrante-profesor/cuadrante-profesor.component';
import { LoginComponent } from './components/login/login.component';

export const routes: Routes = [
  { path: 'cuadrante', component: CuadranteProfesorComponent },
  { path: 'login', component: LoginComponent },
  { path: 'nueva-falta', component: NuevaFaltaComponent },
];
