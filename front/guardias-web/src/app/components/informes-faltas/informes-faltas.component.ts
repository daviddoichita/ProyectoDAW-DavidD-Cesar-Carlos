import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Cuadrante } from '../../interfaces/cuadrante';
import { InformesFaltasService } from '../../services/informes-faltas.service';
import { SelectItem } from 'primeng/api';
import { DropdownModule } from 'primeng/dropdown';
import { SelectButtonModule } from "primeng/selectbutton";


@Component({
  selector: 'app-informes-faltas',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, CommonModule, DropdownModule, SelectButtonModule],
  templateUrl: './informes-faltas.component.html',
  styleUrl: './informes-faltas.component.scss'
})
export class InformesFaltasComponent implements OnInit {
  tipo: string = 'bar';
  tipos: any[] = [];

  cuadrantes: Cuadrante[] = [];
  valorBusqueda: string = '';
  filtros: SelectItem[] = [];
  filtroSeleccionado: any;


  constructor(private informesFaltasService: InformesFaltasService) { }

  ngOnInit(): void {
    this.informesFaltasService.getCuadrantesConFaltas().subscribe(data => {
      this.cuadrantes = data;
    });

    this.filtros = [

      { label: 'Fecha', value: 'fecha' },

      { label: 'Mes', value: 'mes' }

    ];

    this.tipos = [
      { name: 'pi pi-chart-bar', value: 'bar' },
      { name: 'pi pi-chart-line', value: 'line' },
      { name: 'pi pi-chart-pie', value: 'pie' }
    ];

  }

  buscador() {
  }

  verFaltas(): void {
  }
}