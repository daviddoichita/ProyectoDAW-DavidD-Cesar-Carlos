import { RouterModule, Routes } from '@angular/router';
import { NuevaFaltaComponent } from './components/nueva-falta/nueva-falta.component';
import { CuadranteProfesorComponent } from './components/cuadrante-profesor/cuadrante-profesor.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { authGuard } from './guards/auth.guard';
import { SubirSesionesComponent } from './components/subir-sesiones/subir-sesiones.component';
import { InformesFaltasComponent } from './components/informes-faltas/informes-faltas.component';
import { InformesAsistenciasComponent } from './components/informes-asistencias/informes-asistencias.component';
import { InformesIncidenciasComponent } from './components/informes-incidencias/informes-incidencias.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'cuadrante', component: CuadranteProfesorComponent },
  { path: 'login', component: LoginComponent },
  { path: 'nueva-falta', component: NuevaFaltaComponent, canActivate: [authGuard] },
  { path: 'subir-sesiones', component: SubirSesionesComponent, canActivate: [authGuard] },
  { path: 'informes-faltas', component: InformesFaltasComponent, canActivate: [authGuard] },
  { path: 'informes-asistencias', component: InformesAsistenciasComponent, canActivate: [authGuard] },
  { path: 'informes-incidencias', component: InformesIncidenciasComponent, canActivate: [authGuard] },
  { path: 'logout', component: LogoutComponent }
];
