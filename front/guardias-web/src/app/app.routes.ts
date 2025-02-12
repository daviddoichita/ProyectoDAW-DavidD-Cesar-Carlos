import { RouterModule, Routes } from '@angular/router';
import { NuevaFaltaComponent } from './components/nueva-falta/nueva-falta.component';
import { CuadranteProfesorComponent } from './components/cuadrante-profesor/cuadrante-profesor.component';
import { LoginComponent } from './components/login/login.component';
import { ListadoProfesoresComponent } from './components/listado-profesores/listado-profesores.component';
import { AltaProfesorComponent } from './components/alta-profesor/alta-profesor.component';
import { EditarProfesorComponent } from './components/editar-profesor/editar-profesor.component';
import { LogoutComponent } from './components/logout/logout.component';
import { authGuard } from './guards/auth.guard';
import { SubirSesionesComponent } from './components/subir-sesiones/subir-sesiones.component';
import { InformesFaltasComponent } from './components/informes-faltas/informes-faltas.component';
import { InformesAsistenciasComponent } from './components/informes-asistencias/informes-asistencias.component';
import { InformesIncidenciasComponent } from './components/informes-incidencias/informes-incidencias.component';
import { CuadranteDireccionComponent } from './components/cuadrante-direccion/cuadrante-direccion.component';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'cuadrante', component: CuadranteProfesorComponent },
  {
    path: 'cuadrante-direccion',
    component: CuadranteDireccionComponent,
    canActivate: [adminGuard],
  },
  { path: 'login', component: LoginComponent },
  { path: 'listado-profesores', component: ListadoProfesoresComponent },
  { path: 'alta-profesor', component: AltaProfesorComponent },
  { path: 'editar-profesor/:id', component: EditarProfesorComponent },
  { path: 'informes-faltas', component: InformesFaltasComponent, canActivate: [authGuard] },
  { path: 'informes-guardias', component: InformesAsistenciasComponent, canActivate: [authGuard] },
  { path: 'informes-incidencias', component: InformesIncidenciasComponent, canActivate: [authGuard] },
  {
    path: 'nueva-falta',
    component: NuevaFaltaComponent,
    canActivate: [authGuard],
  },
  {
    path: 'subir-sesiones',
    component: SubirSesionesComponent,
    canActivate: [authGuard],
  },
  { path: 'logout', component: LogoutComponent },
];
