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

@Component({
  selector: 'app-nueva-falta',
  standalone: true,
  imports: [DropdownModule, CalendarModule, CommonModule,
    FormsModule, FloatLabelModule, TableModule, ButtonModule,
    CheckboxModule, HeaderComponent, RippleModule, AccordionModule],
  templateUrl: './nueva-falta.component.html',
})
export class NuevaFaltaComponent implements OnInit {

  profesores: { label: string; value: any; }[] = [];

  rangeDates: Date[] | undefined;

  horariosManana: any[] = [];

  horariosTarde: any[] = [];

  profesorSeleccionado: number | null = null;

  asignaturasSeleccionadas: any[] = [];

  constructor(private profesorService: ProfesorService,
    private sesionService: SesionService,
    private faltaService: FaltaService
  ) { }

  ngOnInit() {
    this.cargarProfesoresActivos();
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
    console.log('Profesor seleccionado:', this.profesorSeleccionado);

    if (!this.profesorSeleccionado) return;

    this.sesionService.findByProfesor(this.profesorSeleccionado).subscribe((sesiones: any) => {
      console.log('Respuesta del servicio de sesiones:', sesiones); // <-- Agregado
      
      if (!sesiones || sesiones.length === 0) {
          console.warn('No se recibieron sesiones o la lista está vacía.');
          return;
      }
      
      this.horariosManana = this.organizarSesiones(sesiones, 'mañana');
      this.horariosTarde = this.organizarSesiones(sesiones, 'tarde');
  }, error => {
      console.error('Error al obtener sesiones:', error);
  });
  }

  organizarSesiones(sesiones: any[], turno: 'mañana' | 'tarde') {
    if (!Array.isArray(sesiones)) {
      console.warn('Sesiones inválidas en organizarSesiones:', sesiones);
      return [];
  }
  
    const horas = turno === 'mañana' ? ['08:00', '09:00', '10:00', '11:00', '12:00', '13:00'] : ['15:00', '16:00', '17:00', '18:00'];
    return horas.map(hora => ({
        hora: hora,
        lunes: sesiones.find(s => s.dia === 'Lunes' && s.intervalo.horaInicio === hora) || {},
        martes: sesiones.find(s => s.dia === 'Martes' && s.intervalo.horaInicio === hora) || {},
        miercoles: sesiones.find(s => s.dia === 'Miércoles' && s.intervalo.horaInicio === hora) || {},
        jueves: sesiones.find(s => s.dia === 'Jueves' && s.intervalo.horaInicio === hora) || {},
        viernes: sesiones.find(s => s.dia === 'Viernes' && s.intervalo.horaInicio === hora) || {},
    }));
  }


  guardarFalta() {
    if (!this.profesorSeleccionado || !this.rangeDates || this.asignaturasSeleccionadas.length === 0) {
      alert("Debe completar todos los campos.");
      return;
    }

    const faltaData = {
      profesorId: this.profesorSeleccionado,
      fechaInicio: this.rangeDates[0],
      fechaFin: this.rangeDates[1],
      asignaturas: this.asignaturasSeleccionadas.map(asig => ({
        nombre: asig.nombre,
        hora: asig.hora,
        dia: asig.dia,
        deberes: asig.deberes
      }))
    };

    this.faltaService.registrarFalta(faltaData).subscribe(response => {
      alert("Falta registrada correctamente.");
    }, error => {
      alert("Error al registrar la falta.");
    });
  }
}