import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { RowGroupHeader, RowToggler, SelectableRow, TableModule } from 'primeng/table';
import { ProfesorService } from '../../services/profesor.service';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  selector: 'app-listado-profesores',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, CommonModule],
  templateUrl: './listado-profesores.component.html'
})
export class ListadoProfesoresComponent implements OnInit {

  profesores!: any[];

  filtrarProfesores!: any[];

  profesorSeleccionado: any[] = [];

  searchValue: string = '';

  currentPage: any[] = [];

  constructor(private ProfesorService: ProfesorService, private router: Router) { }

  ngOnInit() {
    this.ProfesorService.findAll().subscribe((profesores: any[]) => {
      this.profesores = profesores;
      this.filtrarProfesores = profesores;
      console.log('Profesores cargados:', this.profesores);
    });
  }

  altaProfesor(): void {
    this.router.navigate(['/alta-profesor']);
  }

  buscador(): void {
    const search = this.searchValue.toLowerCase().trim();
    this.filtrarProfesores = this.profesores.filter(profesor =>
      profesor.nombre.toLowerCase().includes(search) ||
      profesor.apellidos.toLowerCase().includes(search) ||
      profesor.nif.toLowerCase().includes(search) ||
      profesor.email.toLowerCase().includes(search) ||
      profesor.telefono.toString().includes(search)
    );
  }

  delete(profesor: any): void {
    this.ProfesorService.delete(profesor.id).subscribe(() => {
      this.profesores = this.profesores.filter((p: any) => p.id !== profesor.id);
      this.filtrarProfesores = this.filtrarProfesores.filter((p: any) => p.id !== profesor.id);
    });
    window.location.reload();
  }

  deleteSelected(): void {
    for (let profesor of this.profesorSeleccionado) {
      this.ProfesorService.delete(profesor.id).subscribe(() => {
        this.profesores = this.profesores.filter((p: any) => p.id !== profesor.id);
        this.filtrarProfesores = this.filtrarProfesores.filter((p: any) => p.id !== profesor.id);
      });
    }
    window.location.reload();
  }

  /*seleccionarPagina(): void {
    const start = first;
    const end = first +;
    this.currentPage = this.filtrarProfesores.slice(start, end);
  }*/
}