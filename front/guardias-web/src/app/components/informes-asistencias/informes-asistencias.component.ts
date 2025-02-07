import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Cuadrante } from '../../interfaces/cuadrante';
import { SelectItem } from 'primeng/api';
import { DropdownModule } from 'primeng/dropdown';
import { SelectButtonModule } from "primeng/selectbutton";
import { ChartModule } from 'primeng/chart';
import { InformesAsistenciasService } from '../../services/informes-asistencias.service';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-informes-asistencias',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, CommonModule, DropdownModule, SelectButtonModule, ChartModule, CalendarModule, DialogModule],
  templateUrl: './informes-asistencias.component.html',
  styleUrl: './informes-asistencias.component.scss'
})
export class InformesAsistenciasComponent implements OnInit {
  tipo: string = 'table';
  tipos: any[] = [];
  date: Date[] | undefined;

  visible: boolean = false;
  faltaSeleccionada: any = null;

  showDialog(cuadrante: any) {
    this.faltaSeleccionada = cuadrante;
    this.visible = true;
  }

  cuadrantes: Cuadrante[] = [];
  valorBusqueda: string = '';
  filtros: SelectItem[] = [];
  filtroSeleccionado: any;

  barChartData: any;
  lineChartData: any;
  pieChartData: any;
  barChartOptions: any;
  lineChartOptions: any;
  pieChartOptions: any;
  registros: boolean = false;

  constructor(private informesAsistenciasService: InformesAsistenciasService) { }

  ngOnInit(): void {
    this.informesAsistenciasService.getCuadrantesSinFirmar().subscribe(data => {
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
    const meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];

    const faltasPorMes = new Array(12).fill(0);

    this.cuadrantes.forEach(cuadrante => {
      const fecha = new Date(cuadrante.fecha);
      const mes = fecha.getMonth();
      faltasPorMes[mes]++;
    });

    this.registros = faltasPorMes.some(valor => valor > 0);

    this.barChartData = {
      labels: meses,
      datasets: [{
        label: 'Faltas por mes',
        data: faltasPorMes,
        backgroundColor: '#42A5F5'
      }]
    };

    this.lineChartData = {
      labels: meses,
      datasets: [{
        label: 'Faltas por mes',
        data: faltasPorMes,
        fill: false,
        borderColor: '#42A5F5',
        tension: 0.4
      }]
    };

    this.pieChartData = {
      labels: meses,
      datasets: [{
        data: faltasPorMes,
        backgroundColor: [
          '#FF6384',
          '#36A2EB',
          '#FFCE56',
          '#4BC0C0',
          '#9966FF',
          '#FF9F40',
          '#7FE5F0',
          '#B4F8C8',
          '#FFB7B2',
          '#A0522D',
          '#7B68EE',
          '#20B2AA'
        ]
      }]
    };


    this.barChartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      aspectRatio: 0.5,
      scales: {
        x: {
          ticks: {
            font: {
              size: 14
            }
          }
        },
        y: {
          ticks: {
            font: {
              size: 14
            }
          }
        }
      },
      plugins: {
        legend: {
          labels: {
            font: {
              size: 14
            }
          }
        }
      }
    };

    this.lineChartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      aspectRatio: 0.5,
      scales: {
        x: {
          ticks: {
            font: {
              size: 14
            }
          }
        },
        y: {
          ticks: {
            font: {
              size: 14
            }
          }
        }
      },
      plugins: {
        legend: {
          labels: {
            font: {
              size: 14
            }
          }
        }
      }
    };

    this.pieChartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      aspectRatio: 0.5,
      plugins: {
        legend: {
          labels: {
            font: {
              size: 14
            }
          }
        }
      }
    };
  }
}