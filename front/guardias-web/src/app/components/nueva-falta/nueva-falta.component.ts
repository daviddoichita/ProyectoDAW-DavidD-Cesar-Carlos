import { Component, OnInit } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FloatLabelModule } from 'primeng/floatlabel';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { HeaderComponent } from "../header/header.component";
import { RippleModule } from 'primeng/ripple';
import { AccordionModule } from 'primeng/accordion';
import { ProfesorService } from '../../services/profesor.service';
import { SesionService } from '../../services/sesion.service';
import { FaltaService } from '../../services/falta.service';
import { Sesion } from '../../interfaces/sesion';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';


@Component({
  selector: 'app-nueva-falta',
  standalone: true,
  imports: [DropdownModule, CalendarModule, CommonModule,
    FormsModule, FloatLabelModule, TableModule, ButtonModule,
    CheckboxModule, HeaderComponent, RippleModule, AccordionModule,
    ConfirmDialogModule,
  ],
  templateUrl: './nueva-falta.component.html',
    providers: [MessageService, ConfirmDialogModule, ConfirmationService]
})
export class NuevaFaltaComponent implements OnInit {

  profesores: { label: string; value: any; }[] = [];
  sesiones: Sesion[] = [];
  rangeDates: Date[] | undefined;
  horariosManana: any[] = [];
  horariosTarde: any[] = [];
  profesorSeleccionado: number | null = null;
  horarioProfesor: { [dia: string]: any[] } = {};
  asignaturasSeleccionadas: any[] = [];
  diasSemana: string[] = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];
  horasUnicas: string[] = [];

  constructor(private profesorService: ProfesorService,
    private sesionService: SesionService,
    private faltaService: FaltaService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) { }

  ngOnInit() {
    this.cargarProfesoresActivos();
    this.organizarHorario();
  }

  cargarProfesoresActivos(): void {
    this.profesorService.findAll().subscribe((data: any[]) => {
      console.log('Profesores obtenidos', data);
      this.profesores = data.map(profesor => ({
        label: `${profesor.nombre} ${profesor.apellidos}`,
        value: profesor.id
      }));
    });
  }

  cargarHorarioProfesor() {
    if (!this.profesorSeleccionado) {
      console.warn("No se ha seleccionado un profesor.");
      return;
    }
  
    this.sesionService.findByProfesor(this.profesorSeleccionado).subscribe(sesiones => {
      if (!sesiones || sesiones.length === 0) {
        console.log("No hay sesiones para este profesor.");
        this.horarioProfesor = {};
        this.horariosManana = [];
        this.horariosTarde = [];
        return;
      }
  
      console.log("Sesiones obtenidas:", sesiones);
  
      const horarioAgrupado: { [dia: string]: any[] } = {};
      this.horariosManana = [];
      this.horariosTarde = [];
  
      sesiones.forEach(sesion => {
        const dia = sesion.dia.nombre;
        const horaInicio = sesion.intervalo.horaInicio;
  
        if (!horarioAgrupado[dia]) {
          horarioAgrupado[dia] = [];
        }
  
        const sesionInfo = {
          horaInicio: sesion.intervalo.horaInicio,
          horaFin: sesion.intervalo.horaFin,
          materia: sesion.materia.nombre,
          grupo: sesion.grupo ? sesion.grupo.nombre : 'Sin grupo',
          aula: sesion.aula.nombre
        };
  
        horarioAgrupado[dia].push(sesionInfo);
  
        const horaInt = parseInt(horaInicio.toString().split(":")[0], 10);
        if (horaInt < 15) {
          this.horariosManana.push({ ...sesionInfo, dia });
        } else {
          this.horariosTarde.push({ ...sesionInfo, dia });
        }
      });
  
      Object.keys(horarioAgrupado).forEach(dia => {
        horarioAgrupado[dia].sort((a, b) => a.horaInicio.localeCompare(b.horaInicio));
      });
  
      this.horarioProfesor = horarioAgrupado;
      console.log("Horario organizado:", horarioAgrupado);
      console.log("Horario Mañana:", this.horariosManana);
      console.log("Horario Tarde:", this.horariosTarde);
  
      this.organizarHorarioTabla();
    });
  }

  organizarHorario() {
    const todasLasHoras = [
        ...this.horariosManana.map(h => h.horaInicio),
        ...this.horariosTarde.map(h => h.horaInicio)
    ];
    
    this.horasUnicas = [...new Set(todasLasHoras)];
    this.horasUnicas.sort();
}

obtenerMateria(dia: string, hora: string): string {
  const horaNormalizada = hora.padEnd(8, ':00')

  const materia = this.horariosManana.find(h => h.horaInicio === horaNormalizada)?.materia ||
                  this.horariosTarde.find(h => h.horaInicio === horaNormalizada)?.materia || '-';

  return materia;
}


  organizarHorarioTabla() {
    if (!this.horariosManana || !this.horariosTarde) {
      console.error("Los horarios no están definidos correctamente");
      return;
  }
    this.horariosManana = [];
    this.horariosTarde = [];

    const diasSemana = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];

    let horasUnicas = new Set<string>();

    Object.values(this.horarioProfesor).forEach(sesiones => {
        sesiones.forEach(sesion => {
            horasUnicas.add(sesion.horaInicio);
        });
    });

    const horasOrdenadas = Array.from(horasUnicas).sort();

    horasOrdenadas.forEach(hora => {
        let fila: any = { hora };

        diasSemana.forEach(dia => {
            fila[dia.toLowerCase()] = this.horarioProfesor[dia]?.find(sesion => sesion.horaInicio === hora) || null;
        });

        if (hora >= "08:15:00" && hora <= "15:10:00") {
          this.horariosManana.push(fila);
      } else if (hora >= "15:15:00" && hora <= "22:00:00") {
          this.horariosTarde.push(fila);
      }
    });

    console.log("Horario Mañana:", this.horariosManana);
    console.log("Horario Tarde:", this.horariosTarde);
}


seleccionarAsignatura(asignatura: any, hora: string, dia: string) {
  if (!asignatura || !asignatura.materia) {
    return;
  }

  const yaExiste = this.asignaturasSeleccionadas.some(a => 
    a.nombre === asignatura.materia && a.hora === hora && a.dia === dia
  );

  if (!yaExiste) {
    this.asignaturasSeleccionadas.push({
      nombre: asignatura.materia,
      hora,
      dia,
      deberes: false
    });
  }
}

  guardarFalta() {
    if (!this.profesorSeleccionado) {
      alert("Debe seleccionar un profesor.");
      return;
    }
    
    if (this.asignaturasSeleccionadas.length === 0) {
      alert("Debe seleccionar al menos una asignatura.");
      return;
    }

    const faltaData = {
      sesiones: this.sesionesSeleccionadas.map(sesion => ({
        idsesion: sesion.id,
        idcuadrante: this.idCuadranteSeleccionado
      }))
    };

    this.faltaService.registrarFalta(faltaData).subscribe(response => {
      alert("Falta registrada correctamente.");
      this.asignaturasSeleccionadas = [];
      this.rangeDates = undefined;
    }, error => {
      alert("Error al registrar la falta.");
    });
  }

  // filtrarHorariosPorFecha() {
  //   if (!this.rangeDates || this.rangeDates.length < 2) {
  //     console.warn("Debe seleccionar un rango de fechas válido.");
  //     return;
  //   }
  
  //   this.rangeDates = this.rangeDates.filter(date => date !== null);
  //   this.rangeDates.sort((a, b) => (a?.getTime() || 0) - (b?.getTime() || 0));
  
  //   const fechaInicio = this.rangeDates[0];
  //   const fechaFin = this.rangeDates.length > 1 ? this.rangeDates[1] : this.rangeDates[0];
  
  //   if (fechaInicio > fechaFin) {
  //     console.error("El rango de fechas está invertido.");
  //     return;
  //   }
  
  //   console.log("Filtrando horarios para fechas:", fechaInicio, fechaFin);
  //   console.log("Horario profesor antes del filtrado:", this.horarioProfesor);
  
  //   const horariosMananaFiltrados: any[] = [];
  //   const horariosTardeFiltrados: any[] = [];
  
  //   let fechaActual = new Date(fechaInicio);
  //   while (fechaActual <= fechaFin) {
  //     const opciones: Intl.DateTimeFormatOptions = { weekday: "long" };
  //     let diaSemana = fechaActual.toLocaleDateString("es-ES", opciones);
      
  //     diaSemana = diaSemana.charAt(0).toUpperCase() + diaSemana.slice(1);
  
  //     console.log(`Procesando horarios para ${diaSemana} (${fechaActual.toISOString().split('T')[0]})`);
  
  //     if (this.horarioProfesor[diaSemana]) {
  //       this.horarioProfesor[diaSemana].forEach(sesion => {
  //         const sesionFiltrada = { ...sesion, fecha: new Date(fechaActual) };
  //         const horaInt = parseInt(sesion.horaInicio.split(":")[0], 10);
  
  //         if (horaInt < 15) {
  //           horariosMananaFiltrados.push(sesionFiltrada);
  //         } else {
  //           horariosTardeFiltrados.push(sesionFiltrada);
  //         }
  //       });
  //     }
  
  //     fechaActual.setDate(fechaActual.getDate() + 1);
  //   }
  
  //   console.log("Horarios filtrados Mañana:", horariosMananaFiltrados);
  //   console.log("Horarios filtrados Tarde:", horariosTardeFiltrados);
  
  //   this.horariosManana = [...horariosMananaFiltrados];
  //   this.horariosTarde = [...horariosTardeFiltrados];
  // }
  
  cancelar() {
    this.confirmationService.confirm({
      message: '¿Estas seguro que quieres cancelarlo?',
      header: 'Confirmacion',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      acceptLabel: "Aceptar",
      rejectLabel: "Cancelar",
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-danger",
      accept: () => {
        this.messageService.add({ severity: 'info', summary: 'Confirmado', detail: 'Se han cancelado los cambios' })
        setTimeout(() => {
          this.router.navigate(['/cuadrante'])
        }, 1000)
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Cancelado', detail: 'Cancelado' })
      }
    })
  }
}