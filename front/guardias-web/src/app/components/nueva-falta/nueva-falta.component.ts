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

  constructor(private profesorService: ProfesorService) { }

  ngOnInit() {
    this.cargarProfesoresActivos();
  }

  cargarProfesoresActivos(): void {
    this.profesorService.findAll().subscribe((data: any[]) => {
      this.profesores = data.map(profesor => ({
        label: `${profesor.nombre} ${profesor.apellidos}`,
        value: profesor.id
      }));
    });
  }

  horariosManana = [
    { indice: 1, hora: '8:20 - 9:15' },
    { indice: 2, hora: '9:15 - 10:10' },
    { indice: 3, hora: '10:10 - 11:00' },
    { hora: '11:00 - 11:25' },
    { indice: 4, hora: '11:25 - 12:20' },
    { indice: 5, hora: '12:20 - 13:10' },
    { indice: 6, hora: '13:10 - 14:00' },
    { hora: '14:00 - 14:10' },
    { indice: 7, hora: '14:10 - 15:00' },
  ];

  horariosTarde = [
    { indice: 1, hora: '15:15 - 16:05' },
    { indice: 2, hora: '16:05 - 17:00' },
    { indice: 3, hora: '17:00 - 17:50' },
    { hora: '17:50 - 18:10' },
    { indice: 4, hora: '18:10 - 19:00' },
    { indice: 5, hora: '19:00 - 19:50' },
    { indice: 6, hora: '19:50 - 20:40' },
    { hora: '20:40 - 20:50' },
    { indice: 7, hora: '20:50 - 21:40' },
  ];

  asignaturasSeleccionadas = [
    {
      nombre: 'Programacion',
      hora: '8:20 - 9:15',
      dia: 'Lunes',
      deberes: false,
    },
    {
      nombre: 'bbdd',
      hora: '9:15 - 10:10',
      dia: 'Martes',
      deberes: true,
    },
  ];
}