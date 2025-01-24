import { Component, NO_ERRORS_SCHEMA, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { PanelModule } from 'primeng/panel';
import { Table, TableModule } from 'primeng/table';
import { CuadranteService } from '../../services/cuadrante.service';
import { Cuadrante } from '../../interfaces/cuadrante';

@Component({
  selector: 'app-cuadrante-profesor',
  standalone: true,
  imports: [HeaderComponent, PanelModule, TableModule],
  templateUrl: './cuadrante-profesor.component.html',
  styleUrl: './cuadrante-profesor.component.scss',
})
export class CuadranteProfesorComponent implements OnInit {
  cuadrantes!: Cuadrante[];

  getCuadrantes(abrev: string, id: number) {
    return this.cuadrantes.filter(c => c.guardia.dia.abreviacion == abrev && c.guardia.intervalo.id == id)
  }

  constructor(private cuadranteService: CuadranteService) { }

  ngOnInit(): void {
    this.cuadranteService.findCurrentWeek().subscribe(
      {
        next: (cuadrantes) => {
          this.cuadrantes = cuadrantes
        },
        error: (error) => {
          console.error(error);
        },
      }
    );
  }
}
