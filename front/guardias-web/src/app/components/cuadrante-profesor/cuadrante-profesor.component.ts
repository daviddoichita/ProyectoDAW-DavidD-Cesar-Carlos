import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';
import { CuadranteService } from '../../services/cuadrante.service';
import { Cuadrante } from '../../interfaces/cuadrante';
import { Intervalo } from '../../interfaces/intervalo';
import { IntervalosService } from '../../services/intervalos.service';
import { CommonModule } from '@angular/common';
import { AccordionModule } from 'primeng/accordion';
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule } from '@angular/forms';
import { DiaService } from '../../services/dia.service';
import { ConfirmationService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { Router } from '@angular/router';
import { ConfirmationDialogTemplatesService } from '../../services/confirmation-dialog-templates.service';
import { GlobalStateService } from '../../services/global-state.service';

@Component({
  selector: 'app-cuadrante-profesor',
  standalone: true,
  imports: [HeaderComponent, PanelModule, TableModule, CommonModule, AccordionModule, SelectButtonModule, FormsModule, ConfirmDialogModule],
  templateUrl: './cuadrante-profesor.component.html',
  styleUrl: './cuadrante-profesor.component.scss',
  providers: [ConfirmationService]
})
export class CuadranteProfesorComponent implements OnInit {
  cuadrantes: Cuadrante[] = [];
  intervalos: Intervalo[] = [];
  dias: any[] = [];
  dia!: string;

  constructor(
    private cuadranteService: CuadranteService,
    private intervaloService: IntervalosService,
    private diaService: DiaService,
    private confirmationService: ConfirmationService,
    private confirmationDialogTemplatesService: ConfirmationDialogTemplatesService,
    private globalStateService: GlobalStateService,
    private router: Router
  ) { }

  ngOnInit() {
    this.cuadranteService.findAll()
    this.globalStateService.confirmDialog.subscribe({
      next: (dialog) => {
        if (dialog) {
          this.globalStateService.clearConfirmDialog()
          this.confirmationService.confirm(dialog)
        }
      }
    })

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

    const dias = sessionStorage.getItem('dias')
    if (dias) {
      this.dias = JSON.parse(dias)
    } else {
      this.diaService.findCurrentWeek().subscribe({
        next: (dias) => {
          dias.forEach((dia) => {
            this.dias.push({ name: dia.nombre, value: dia.abreviacion })
            this.dia = this.dias[0].value
          })
        },
        error: (error) => {
          console.error(error)
        }
      })
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
