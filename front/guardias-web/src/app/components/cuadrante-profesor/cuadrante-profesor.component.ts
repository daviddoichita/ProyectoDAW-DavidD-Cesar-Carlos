import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { Panel, PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';
import { CuadranteService } from '../../services/cuadrante.service';
import { Cuadrante } from '../../interfaces/cuadrante';
import { Intervalo } from '../../interfaces/intervalo';
import { IntervalosService } from '../../services/intervalos.service';
import { CommonModule } from '@angular/common';
import { AccordionModule } from 'primeng/accordion';
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cuadrante-profesor',
  standalone: true,
  imports: [HeaderComponent, PanelModule, TableModule, CommonModule, AccordionModule, SelectButtonModule, FormsModule],
  templateUrl: './cuadrante-profesor.component.html',
  styleUrl: './cuadrante-profesor.component.scss',
})
export class CuadranteProfesorComponent implements OnInit {
  cuadrantes: Cuadrante[] = [];
  intervalos: Intervalo[] = [];
  dia: string = 'L';
  dias: any[] = [
    { name: 'Lunes', value: 'L' },
    { name: 'Martes', value: 'M' },
    { name: 'Miercoles', value: 'X' },
    { name: 'Jueves', value: 'J' },
    { name: 'Viernes', value: 'V' }
  ];

  constructor(private cuadranteService: CuadranteService, private intervaloService: IntervalosService) { }

  ngOnInit() {
    const cuadrantes = sessionStorage.getItem('cuadrantes')
    if (cuadrantes) {
      this.cuadrantes = JSON.parse(cuadrantes)
    } else {
      this.cuadranteService.findCurrentWeek().subscribe(
        {
          next: (cuadrantes) => {
            this.cuadrantes = cuadrantes
            sessionStorage.setItem("cuadrantes", JSON.stringify(cuadrantes))
          },
          error: (error) => {
            console.error(error);
          },
        }
      );
    }

    const intervalos = sessionStorage.getItem('intervalos')
    if (intervalos) {
      this.intervalos = JSON.parse(intervalos)
    } else {
      this.intervaloService.findAll().subscribe(
        {
          next: (intervalos) => {
            this.intervalos = intervalos
            sessionStorage.setItem("intervalos", JSON.stringify(intervalos))
          },
          error: (error) => {
            console.error(error)
          }
        }
      )
    }
  }

  getCuadrantes(abrev: string, id: number) {
    return this.cuadrantes.filter(c => c.guardia.dia.abreviacion == this.dia && c.guardia.intervalo.id == id)
  }

  getNombreHora(hora: number): string | undefined {
    const intervalo = this.intervalos.find(i => i.id == hora)
    if (intervalo) {
      return `${intervalo.horaInicio} - ${intervalo.horaFin}`
    }
    return undefined
  }
}
