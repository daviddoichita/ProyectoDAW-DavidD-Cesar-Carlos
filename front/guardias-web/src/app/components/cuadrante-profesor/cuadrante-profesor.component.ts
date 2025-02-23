import { Component, EventEmitter, OnDestroy, OnInit } from '@angular/core';
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
import { ConfirmationService, Message, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { NavigationStart, Router } from '@angular/router';
import { GlobalStateService } from '../../services/global-state.service';
import { AuthService } from '../../services/auth.service';
import { ButtonModule } from 'primeng/button';
import { Profesor } from '../../interfaces/profesor';
import { ToastModule } from 'primeng/toast';
import { HttpStatusCode } from '@angular/common/http';
import { DialogModule } from 'primeng/dialog';
import { Dia } from '../../interfaces/dia';
import { Subscription } from 'rxjs';

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
    ToastModule,
    DialogModule,
  ],
  templateUrl: './cuadrante-profesor.component.html',
  styleUrl: './cuadrante-profesor.component.scss',
  providers: [ConfirmationService, MessageService],
})
export class CuadranteProfesorComponent implements OnInit, OnDestroy {
  cuadrantes: Cuadrante[] = [];
  cuadrantesLoaded: boolean = false;
  isAdmin: boolean = false;
  user!: Profesor;

  firmando: boolean = false;
  adding: boolean = false;
  dialogVisible: boolean = false;
  incidencia: string = '';
  idCuadrante: number = 0;
  idFalta: number = 0;

  intervalos: Intervalo[] = [];

  dias: any[] = [];
  dia!: string;
  diaObj!: Dia;

  activeIndex!: number[];

  private navStartSub: Subscription | undefined;

  constructor(
    private cuadranteService: CuadranteService,
    private intervaloService: IntervalosService,
    private diaService: DiaService,
    private confirmationService: ConfirmationService,
    private globalStateService: GlobalStateService,
    public router: Router,
    private auth: AuthService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    sessionStorage.setItem('firstEntry', 'no');

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
          const dias = sessionStorage.getItem('dias');
          if (dias) {
            this.dias = JSON.parse(dias);
          } else {
            this.diaService.findCurrentWeek().subscribe({
              next: (dias) => {
                dias.forEach((dia) => {
                  this.dias.push({ name: dia.nombre, value: dia.abreviacion });
                });
                const diaSession = sessionStorage.getItem('dia');
                if (diaSession) {
                  this.dia = diaSession;
                } else {
                  this.dia = this.dias[0].value;
                }
                this.diaObj = dias.filter((d) => d.nombre === this.dia)[0];
                this.openHora();
                const intervalos = sessionStorage.getItem('intervalos');
                if (intervalos) {
                  this.intervalos = JSON.parse(intervalos);
                } else {
                  this.intervaloService.findAll().subscribe({
                    next: (intervalos) => {
                      intervalos.forEach((i) => console.log(i));
                      this.intervalos = intervalos.filter((i) => i.id != 4);
                      sessionStorage.setItem(
                        'intervalos',
                        JSON.stringify(this.intervalos)
                      );
                    },
                    error: (error) => {
                      console.error(error);
                    },
                  });
                  // if (this.user) {
                  //   this.intervaloService
                  //     .findIntervalosGuardiasProfesorByDia(
                  //       this.user.id,
                  //       this.diaObj.id
                  //     )
                  //     .subscribe({
                  //       next: (intervalos) => {
                  //         intervalos.forEach((i) => console.log(i));
                  //         this.intervalos = intervalos.filter((i) => i.id != 4);
                  //         sessionStorage.setItem(
                  //           'intervalos',
                  //           JSON.stringify(this.intervalos)
                  //         );
                  //       },
                  //     });
                  // } else {
                  // }
                }
              },
              error: (error) => {
                console.error(error);
              },
            });
          }
        } else {
          const dias = sessionStorage.getItem('dias');
          if (dias) {
            this.dias = JSON.parse(dias);
          } else {
            this.diaService.findCurrentWeek().subscribe({
              next: (dias) => {
                dias.forEach((dia) => {
                  this.dias.push({ name: dia.nombre, value: dia.abreviacion });
                });
                const diaSession = sessionStorage.getItem('dia');
                if (diaSession) {
                  this.dia = diaSession;
                } else {
                  this.dia = this.dias[0].value;
                }
                this.diaObj = dias.filter((d) => d.nombre === this.dia)[0];
                this.openHora();
                const intervalos = sessionStorage.getItem('intervalos');
                if (intervalos) {
                  this.intervalos = JSON.parse(intervalos);
                } else {
                  this.intervaloService.findAll().subscribe({
                    next: (intervalos) => {
                      intervalos.forEach((i) => console.log(i));
                      this.intervalos = intervalos.filter((i) => i.id != 4);
                      sessionStorage.setItem(
                        'intervalos',
                        JSON.stringify(this.intervalos)
                      );
                    },
                    error: (error) => {
                      console.error(error);
                    },
                  });
                  // if (this.user) {
                  //   this.intervaloService
                  //     .findIntervalosGuardiasProfesorByDia(
                  //       this.user.id,
                  //       this.diaObj.id
                  //     )
                  //     .subscribe({
                  //       next: (intervalos) => {
                  //         intervalos.forEach((i) => console.log(i));
                  //         this.intervalos = intervalos.filter((i) => i.id != 4);
                  //         sessionStorage.setItem(
                  //           'intervalos',
                  //           JSON.stringify(this.intervalos)
                  //         );
                  //       },
                  //     });
                  // } else {
                  // }
                }
              },
              error: (error) => {
                console.error(error);
              },
            });
          }
        }
      },
      error: (error) => {
        console.error(error);
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

    this.navStartSub = this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.saveState();
      }
    });

    window.onbeforeunload = () => this.saveState();
  }

  ngOnDestroy(): void {
    if (this.navStartSub) {
      this.navStartSub.unsubscribe();
    }
  }

  activeIndexChange(v: number | number[]) {
    if (Array.isArray(v)) {
      this.activeIndex = v;
    }
  }

  saveState() {
    sessionStorage.setItem('activeIndex', this.activeIndex.toString());
    sessionStorage.setItem('dia', this.dia);
  }

  openHora() {
    const firstEntry = sessionStorage.getItem('firstEntry');
    if (firstEntry) {
      const activeIndex = sessionStorage.getItem('activeIndex');
      if (activeIndex) {
        this.activeIndex = [];
        for (let i of activeIndex.split(',')) {
          this.activeIndex.push(Number(i));
        }
        return;
      }
    }

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
      }
      this.activeIndex = [0];
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
    this.firmando = true;
    this.cuadranteService.firmar(idCuadrante, idFalta, 'firmado').subscribe({
      next: (response) => {
        if (Number(response) === 0) {
          this.firmando = false;
          this.cuadrantesLoaded = false;
          this.cuadranteService.findCurrentWeek().subscribe({
            next: (cuadrantes) => {
              this.cuadrantes = cuadrantes.sort(
                (a, b) => a.cargo.id - b.cargo.id
              );
              this.cuadrantesLoaded = true;
              this.messageService.add({
                severity: 'success',
                summary: 'Exito',
                detail: 'Firmado correcto',
              });
            },
            error: (error) => {
              console.error(error);
            },
          });
        }
      },
      error: (error) => {
        if (error.status === HttpStatusCode.BadRequest) {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Este cuadrante no se puede firmar fuera de su hora',
          });
        } else if (error.status === HttpStatusCode.Unauthorized) {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No tienes permiso para firmar este cuadrante',
          });
        } else if (error.status === HttpStatusCode.InternalServerError) {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Error al firmar el cuadrante',
          });
        }
      },
    });
  }

  showIncidenciaDialog(idCuadrante: number, idFalta: number) {
    this.idCuadrante = idCuadrante;
    this.idFalta = idFalta;
    this.dialogVisible = true;
  }

  addIncidencia() {
    this.dialogVisible = false;
    this.firmando = true;
    this.cuadranteService
      .addIncidencia(this.idCuadrante, this.idFalta, this.incidencia)
      .subscribe({
        next: (response) => {
          this.firmando = false;
          if (Number(response) === 0) {
            this.adding = true;
            this.cuadranteService.findCurrentWeek().subscribe({
              next: (cuadrantes) => {
                this.cuadrantes = cuadrantes;
                this.adding = false;
                this.messageService.add({
                  severity: 'success',
                  summary: 'Exito',
                  detail: 'Incidencia asignada',
                });
              },
              error: (error) => {
                console.error(error);
              },
            });
          }
        },
        error: (error) => {
          this.firmando = false;
          if (error.status === HttpStatusCode.BadRequest) {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Este cuadrante no se puede editar en este momento',
            });
          } else if (error.status === HttpStatusCode.Unauthorized) {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'No tienes permiso para editar este cuadrante',
            });
          } else if (error.status === HttpStatusCode.InternalServerError) {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al editar el cuadrante',
            });
          }
        },
      });
  }

  mostrarInfoProf(profesor: Profesor) {
    this.confirmationService.confirm({
      target: document.body as EventTarget,
      message: `Nombre: ${profesor.nombre} ${profesor.apellidos} <br>Email: ${profesor.email} <br> Telefono: ${profesor.telefono}`,
      header: 'Informacion profesor',
      icon: 'pi pi-info-circle',
      acceptIcon: 'none',
      acceptLabel: 'Aceptar',
      rejectVisible: false,
      accept: () => {},
      reject: () => {},
    });
  }

  mostrarIncidencia(incidencia: string) {
    this.confirmationService.confirm({
      target: document.body as EventTarget,
      message: incidencia,
      header: 'Informacion incidencia',
      icon: 'pi pi-info-circle',
      acceptIcon: 'none',
      acceptLabel: 'Aceptar',
      rejectVisible: false,
      accept: () => {},
      reject: () => {},
    });
  }
}
