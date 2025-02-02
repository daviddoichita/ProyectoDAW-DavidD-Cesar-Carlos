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
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-informes-faltas',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, CommonModule, DropdownModule, SelectButtonModule, ChartModule],
  templateUrl: './informes-faltas.component.html',
  styleUrl: './informes-faltas.component.scss'
})
export class InformesFaltasComponent implements OnInit {
  tipo: string = 'table';
  tipos: any[] = [];

  cuadrantes: Cuadrante[] = [];
  valorBusqueda: string = '';
  filtros: SelectItem[] = [];
  filtroSeleccionado: any;

  barChartData: any;
  lineChartData: any;
  pieChartData: any;

  constructor(private informesFaltasService: InformesFaltasService) { }

  ngOnInit(): void {
    this.informesFaltasService.getCuadrantesConFaltas().subscribe(data => {
      this.cuadrantes = data;
      this.initializeChartData();
    });

    this.filtros = [
      { label: 'Fecha', value: 'fecha' },
      { label: 'Mes', value: 'mes' }
    ];

    this.tipos = [
      { name: '', icon: 'pi pi-chart-bar', value: 'bar' },
      { name: '', icon: 'pi pi-chart-line', value: 'line' },
      { name: '', icon: 'pi pi-chart-pie', value: 'pie' },
      { name: '', icon: 'pi pi-table', value: 'table' },
    ];
  }

  buscador() {
  }

  verFaltas(): void {
  }

  initializeChartData() {
    this.barChartData = {
      labels: ['Label1', 'Label2', 'Label3'],
      datasets: [
        {
          label: 'Dataset 1',
          data: [65, 59, 80],
          backgroundColor: '#42A5F5',
        },
        {
          label: 'Dataset 2',
          data: [28, 48, 40],
          backgroundColor: '#66BB6A',
        }
      ]
    };

    this.lineChartData = {
      labels: ['Label1', 'Label2', 'Label3'],
      datasets: [
        {
          label: 'Dataset 1',
          data: [65, 59, 80],
          fill: false,
          borderColor: '#42A5F5'
        },
        {
          label: 'Dataset 2',
          data: [28, 48, 40],
          fill: false,
          borderColor: '#66BB6A'
        }
      ]
    };

    this.pieChartData = {
      labels: ['Label1', 'Label2', 'Label3'],
      datasets: [
        {
          data: [300, 50, 100],
          backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
          hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
        }
      ]
    };
  }
}