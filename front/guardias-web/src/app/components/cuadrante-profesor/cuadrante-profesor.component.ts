import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-cuadrante-profesor',
  standalone: true,
  imports: [HeaderComponent, PanelModule],
  templateUrl: './cuadrante-profesor.component.html',
  styleUrl: './cuadrante-profesor.component.scss',
})
export class CuadranteProfesorComponent {}
