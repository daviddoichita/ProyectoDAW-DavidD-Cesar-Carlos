import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Table } from 'primeng/table';
import { TableHeaderCheckboxToggleEvent } from 'primeng/table';

@Component({
  selector: 'app-informes-faltas',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, CommonModule],
  templateUrl: './informes-faltas.component.html',
  styleUrl: './informes-faltas.component.scss'
})
export class InformesFaltasComponent {

  valorBusqueda: string = '';

  filtrarProfesores: any[] = [];

  verFaltas(profesor: any): void {

    console.log(profesor);
  }

  buscador() {
  }
}
