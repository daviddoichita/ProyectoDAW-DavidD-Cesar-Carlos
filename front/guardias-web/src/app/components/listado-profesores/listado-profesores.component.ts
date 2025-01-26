import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { TableModule } from 'primeng/table';
import { ProfesorService } from '../../services/profesor.service';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Table } from 'primeng/table';
import { TableHeaderCheckboxToggleEvent } from 'primeng/table';

@Component({
  selector: 'app-listado-profesores',
  standalone: true,
  imports: [
    HeaderComponent,
    TableModule,
    ButtonModule,
    InputTextModule,
    FormsModule,
    CommonModule,
  ],
  templateUrl: './listado-profesores.component.html',
})
export class ListadoProfesoresComponent implements OnInit {
  profesores!: any[];

  filtrarProfesores!: any[];

  profesorSeleccionado: any[] = [];

  valorBusqueda: string = '';

  profesorSeleccionadosIds: Set<number> = new Set<number>();

  checkboxTh: boolean = false;

  @ViewChild('table', { static: false }) table!: Table;

  constructor(
    private ProfesorService: ProfesorService,
    private router: Router
  ) {}

  ngOnInit() {
    this.ProfesorService.findAll().subscribe((profesores: any[]) => {
      this.profesores = profesores;
      this.filtrarProfesores = profesores;
      this.sincronizar();
      console.log('Profesores cargados:', this.profesores);
    });
  }

  altaProfesor(): void {
    this.router.navigate(['/alta-profesor']);
  }

  editarProfesor(profesor: any): void {
    this.router.navigate(['/editar-profesor', profesor.id]);
  }  

  buscador(): void {
    const buscar = this.valorBusqueda.toLowerCase().trim();
    this.filtrarProfesores = this.profesores.filter(
      (profesor) =>
        profesor.nombre.toLowerCase().includes(buscar) ||
        profesor.apellidos.toLowerCase().includes(buscar) ||
        profesor.nif.toLowerCase().includes(buscar) ||
        profesor.email.toLowerCase().includes(buscar) ||
        profesor.telefono.toString().includes(buscar)
    );
    this.sincronizar();
  }

  delete(profesor: any): void {
    this.ProfesorService.delete(profesor.id).subscribe(() => {
      this.profesores = this.profesores.filter(
        (p: any) => p.id !== profesor.id
      );
      this.filtrarProfesores = this.filtrarProfesores.filter(
        (p: any) => p.id !== profesor.id
      );
      this.profesorSeleccionadosIds.delete(profesor.id);
    });
    window.location.reload();
  }

  deleteSeleccionado(): void {
    for (let profesor of this.profesorSeleccionado) {
      this.ProfesorService.delete(profesor.id).subscribe(() => {
        this.profesores = this.profesores.filter(
          (p: any) => p.id !== profesor.id
        );
        this.filtrarProfesores = this.filtrarProfesores.filter(
          (p: any) => p.id !== profesor.id
        );
        this.profesorSeleccionadosIds.delete(profesor.id);
      });
    }
    window.location.reload();
  }

  seleccionarPagina(): void {
    this.sincronizar();
  }

  filaSeleccionada(profesor: any): void {
    this.profesorSeleccionadosIds.add(profesor.id);
    this.sincronizar();
  }

  filaNoSeleccionada(profesor: any): void {
    this.profesorSeleccionadosIds.delete(profesor.id);
    this.sincronizar();
  }

  headerCheckbox(event: TableHeaderCheckboxToggleEvent): void {
    this.checkboxTh = event.checked;

    const empezar = this.table.first || 0;
    const terminar = empezar + (this.table.rows || 10);
    const paginaActual = this.filtrarProfesores.slice(empezar, terminar);

    if (this.checkboxTh) {
      paginaActual.forEach((profesor) =>
        this.profesorSeleccionadosIds.add(profesor.id)
      );
    } else {
      paginaActual.forEach((profesor) =>
        this.profesorSeleccionadosIds.delete(profesor.id)
      );
    }
    this.sincronizar();
  }

  private sincronizar(): void {
    if (!this.table) return;

    const empezar = this.table.first || 0;
    const terminar = empezar + (this.table.rows || 10);
    const paginaActual = this.filtrarProfesores.slice(empezar, terminar);

    this.profesorSeleccionado = paginaActual.filter((profesor) =>
      this.profesorSeleccionadosIds.has(profesor.id)
    );

    this.checkboxTh =
      paginaActual.length > 0 &&
      paginaActual.every((profesor) =>
        this.profesorSeleccionadosIds.has(profesor.id)
      );
  }
}