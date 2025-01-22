import { Routes } from '@angular/router';
import { NuevaFaltaComponent } from './components/nueva-falta/nueva-falta.component';
import { CuadranteProfesorComponent } from './components/cuadrante-profesor/cuadrante-profesor.component';
import { LoginComponent } from './components/login/login.component';
import { ListadoProfesoresComponent } from './components/listado-profesores/listado-profesores.component';
import { AltaProfesorComponent } from './components/alta-profesor/alta-profesor.component';

export const routes: Routes = [
  { path: 'cuadrante', component: CuadranteProfesorComponent },
  { path: 'login', component: LoginComponent },
  { path: 'nueva-falta', component: NuevaFaltaComponent },
  { path: 'listado-profesores', component: ListadoProfesoresComponent },
  { path: 'alta-profesor', component: AltaProfesorComponent },
];
