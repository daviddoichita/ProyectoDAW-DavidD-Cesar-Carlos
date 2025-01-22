import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { TableModule } from 'primeng/table';
import { ProfesorService } from '../../services/profesor.service';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-alta-profesor',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule],
  templateUrl: './alta-profesor.component.html'
})
export class AltaProfesorComponent implements OnInit {

  profesores!: any[];

  filtrarProfesores!: any[];

  constructor(private ProfesorService: ProfesorService) { }

  ngOnInit() {
    this.ProfesorService.findById().subscribe((data: any[]) => {
      this.profesores = data;
      this.filtrarProfesores = data;
    });
  }

  update(profesor: any): void {
    this.ProfesorService.update(profesor).subscribe(() => {
      this.profesores = this.profesores.map((p: any) => {
        if (p.id === profesor.id) {
          return profesor;
        }
        if (p.nombre === profesor.nombre) {
          return profesor;
        }
        if (p.apellidos === profesor.apellidos) {
          return profesor;
        }
        if (p.nif === profesor.nif) {
          return profesor;
        }
        if (p.email === profesor.email) {
          return profesor;
        }
        if (p.telefono === profesor.telefono) {
          return profesor;
        }
        return p;
      });
    });
  }
}