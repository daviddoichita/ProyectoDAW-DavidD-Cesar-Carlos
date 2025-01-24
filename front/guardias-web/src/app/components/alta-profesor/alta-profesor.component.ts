import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DropdownModule } from 'primeng/dropdown';
import { HttpHeaders } from '@angular/common/http';
import { ProfesorService } from '../../services/profesor.service';



@Component({
  selector: 'app-alta-profesor',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, DropdownModule],
  templateUrl: './alta-profesor.component.html'
})
export class AltaProfesorComponent implements OnInit {

  nombre: string = '';
  apellidos: string = '';
  contrasenya: string = '';
  nif: string = '';
  direccion: string = '';
  email: string = '';
  telefono: string = '';


  profesores!: any[];

  constructor(
    private profesorService: ProfesorService,
    private router: Router) { }

  ngOnInit() {
    this.profesorService.findById().subscribe((data: any[]) => {
      this.profesores = data.map(profesor => ({
        label: profesor.nombre,
        value: profesor.id
      }));
    });
  }

  sustituye: any = this.profesores;

  guardar(): void {
    const nuevoProfesor = {
      nombre: this.nombre,
      apellidos: this.apellidos,
      contrasenya: this.contrasenya,
      nif: this.nif,
      direccion: this.direccion,
      email: this.email,
      telefono: this.telefono,
      sustituye: this.sustituye
    };

    this.profesorService.save(nuevoProfesor).subscribe(() => {
      this.router.navigate(['/listado-profesores']);
    });
  }

  cancelar(): void {
    this.router.navigate(['/listado-profesores']);
  }
}