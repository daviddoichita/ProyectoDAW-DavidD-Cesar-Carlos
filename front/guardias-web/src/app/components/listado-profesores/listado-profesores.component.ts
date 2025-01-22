import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { TableModule } from 'primeng/table';
import { ProfesorService } from '../../services/profesor.service';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-listado-profesores',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule],
  templateUrl: './listado-profesores.component.html'
})
export class ListadoProfesoresComponent implements OnInit {

  profesores!: any[];

  filtrarProfesores!: any[];

  searchValue: string = '';

  constructor(private ProfesorService: ProfesorService) { }

  ngOnInit() {
    this.ProfesorService.findAll().subscribe((profesores: any[]) => {
      this.profesores = profesores;
      this.filtrarProfesores = profesores;
      console.log(profesores)
    });
  }

  buscador(): void {
    const search = this.searchValue.toLowerCase().trim();
    this.filtrarProfesores = [];
    this.filtrarProfesores.push(...this.profesores.filter(profesor =>
      profesor.nombre.toLowerCase().includes(search)
    ));
    this.filtrarProfesores.push(...this.profesores.filter(profesor =>
      profesor.apellidos.toLowerCase().includes(search)
    ));
    this.filtrarProfesores.push(...this.profesores.filter(profesor =>
      profesor.nif.toLowerCase().includes(search)
    ));
    this.filtrarProfesores.push(...this.profesores.filter(profesor =>
      profesor.email.toLowerCase().includes(search)
    ));
    this.filtrarProfesores.push(...this.profesores.filter(profesor =>
      profesor.telefono.toString().includes(search)
    ));
  }

  delete(profesor: any): void {
    this.ProfesorService.delete(profesor.id).subscribe(() => {
      this.profesores = this.profesores.filter((p: any) => p.id !== profesor.id);
    });
  }
}