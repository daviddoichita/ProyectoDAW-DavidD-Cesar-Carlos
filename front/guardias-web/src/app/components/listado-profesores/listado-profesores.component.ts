import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { TableModule } from 'primeng/table';
import { ProfesorService } from '../../services/profesor.service';
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

  profesores!: any[];

  rangeDates: Date[] | undefined;

  constructor(private ProfesorService: ProfesorService) { }

  ngOnInit() {
    this.ProfesorService.findAll().subscribe((profesores: any[]) => {
      this.profesores = profesores;
      console.log(profesores)
    });
  }
}