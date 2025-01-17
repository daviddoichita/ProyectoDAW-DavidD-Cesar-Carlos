import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { TableModule } from 'primeng/table';
import { ProfesorService } from '../services/profesorService';
import { Button } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';


@Component({
  selector: 'app-listado-profesores',
  standalone: true,
  imports: [HeaderComponent, TableModule, Button, IconFieldModule, InputIconModule],
  templateUrl: './listado-profesores.component.html'
})
export class ListadoProfesoresComponent implements OnInit {

  profesores: any[] | undefined;

  rangeDates: Date[] | undefined;

  prueba = [
    { id: 1, nombre: 'Pepe', apellidos: 'Pérez', nif: '1234321', email: 'dfgdfg', telefono: '123123123' },
  ]

  constructor(private ProfesorService: ProfesorService) { }

  ngOnInit() {
    this.ProfesorService.findAll().subscribe(profesores => {
      this.profesores = profesores;
      console.log(profesores)
    });
  }
}