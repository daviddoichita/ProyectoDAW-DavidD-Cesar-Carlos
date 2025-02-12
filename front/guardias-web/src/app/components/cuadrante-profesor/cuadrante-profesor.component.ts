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
import { GlobalStateService } from '../../services/global-state.service';
import { AuthService } from '../../services/auth.service';
import { ButtonModule } from 'primeng/button';
import { Profesor } from '../../interfaces/profesor';

@Component({
  selector: 'app-cuadrante-profesor',
  standalone: true,
  imports: [
    HeaderComponent,
    PanelModule,
    TableModule,
    CommonModule,
    AccordionModule,
    SelectButtonModule,
    FormsModule,
    ConfirmDialogModule,
    ButtonModule,
  ],
  templateUrl: './cuadrante-profesor.component.html',
  styleUrl: './cuadrante-profesor.component.scss',
  providers: [ConfirmationService],
})
export class CuadranteProfesorComponent implements OnInit {
  cuadrantes: Cuadrante[] = [];
  cuadrantesLoaded: boolean = false;
  isAdmin: boolean = false;
  user!: Profesor;
  firmando: boolean = false;

  intervalos: Intervalo[] = [];

  dias: any[] = [];
  dia!: string;

  activeIndex!: number[];

  constructor(
    private cuadranteService: CuadranteService,
    private intervaloService: IntervalosService,
    private diaService: DiaService,
    private confirmationService: ConfirmationService,
    private globalStateService: GlobalStateService,
    public router: Router,
    private auth: AuthService
  ) {}

  ngOnInit() {
    this.globalStateService.confirmDialog.subscribe({
      next: (dialog) => {
        if (dialog) {
          this.globalStateService.clearConfirmDialog();
          this.confirmationService.confirm(dialog);
        }
      },
    });

    this.auth.me().subscribe({
      next: (response) => {
        if (response) {
          this.user = response;
        }
      },
    });

    this.auth.getAuthLevel().subscribe({
      next: (response) => {
        this.isAdmin = response;
      },
    });

    this.cuadranteService.findCurrentWeek().subscribe({
      next: (cuadrantes) => {
        this.cuadrantes = cuadrantes.sort((a, b) => a.cargo.id - b.cargo.id);
        this.cuadrantesLoaded = true;
      },
      error: (error) => {
        console.error(error);
      },
    });

    const dias = sessionStorage.getItem('dias');
    if (dias) {
      this.dias = JSON.parse(dias);
    } else {
      this.diaService.findCurrentWeek().subscribe({
        next: (dias) => {
          dias.forEach((dia) => {
            this.dias.push({ name: dia.nombre, value: dia.abreviacion });
          });
          this.dia = this.dias[0].value;
          this.openHora();
        },
        error: (error) => {
          console.error(error);
        },
      });
    }

    const intervalos = sessionStorage.getItem('intervalos');
    if (intervalos) {
      this.intervalos = JSON.parse(intervalos);
    } else {
      this.intervaloService.findAll().subscribe({
        next: (intervalos) => {
          intervalos.forEach((i) => console.log(i));
          this.intervalos = intervalos.filter((i) => i.id != 4);
          sessionStorage.setItem('intervalos', JSON.stringify(this.intervalos));
        },
        error: (error) => {
          console.error(error);
        },
      });
    }
  }

  openHora() {
    const hour = new Date().getHours();
    if (hour < 8) {
      this.activeIndex = [0];
    } else if (hour < 9) {
      this.activeIndex = [1];
    } else if (hour < 10) {
      this.activeIndex = [2];
    } else if (hour < 11) {
      this.activeIndex = [3];
    } else if (hour < 12) {
      this.activeIndex = [4];
    } else if (hour < 13) {
      this.activeIndex = [5];
    } else {
      if (this.dias.length > 1) {
        this.dia = this.dias[1].value;
        this.activeIndex = [0];
      }
    }
  }

  getCuadrantes(id: number) {
    return this.cuadrantes.filter(
      (c) =>
        c.guardia.dia.abreviacion == this.dia && c.guardia.intervalo.id == id
    );
  }

  getNombreHora(hora: number): string | undefined {
    const intervalo = this.intervalos.find((i) => i.id == hora);
    if (intervalo) {
      return `${intervalo.horaInicio} - ${intervalo.horaFin}`;
    }
    return undefined;
  }

  firmarCuadrante(idCuadrante: number, idFalta: number) {
    this.cuadranteService.firmar(idCuadrante, idFalta, 'firmado').subscribe({
      next: (response) => {
        console.log(response);
        this.firmando = true;
        this.cuadranteService.findCurrentWeek().subscribe({
          next: (cuadrantes) => {
            this.cuadrantes = cuadrantes;
            this.firmando = false;
          },
          error: (error) => {
            console.error(error);
          },
        });
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  mostrarInfoProf(profesor: Profesor) {
    this.confirmationService.confirm({
      target: document.body as EventTarget,
      message: `Nombre: ${profesor.nombre} ${profesor.apellidos} <br>Email: ${profesor.email} <br> Telefono: ${profesor.telefono}`,
      header: 'Informacion profesor',
      icon: 'pi pi-info',
      acceptIcon: 'none',
      acceptLabel: 'Aceptar',
      rejectVisible: false,
      accept: () => {},
      reject: () => {},
    });
  }
}
