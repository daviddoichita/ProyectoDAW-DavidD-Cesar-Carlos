import { Routes } from '@angular/router';
import { NuevaFaltaComponent } from './components/nueva-falta/nueva-falta.component';
import { CuadranteProfesorComponent } from './components/cuadrante-profesor/cuadrante-profesor.component';

export const routes: Routes = [
    { path: 'nueva-falta', component: NuevaFaltaComponent },
    { path: 'cuadrante', component: CuadranteProfesorComponent }
];
