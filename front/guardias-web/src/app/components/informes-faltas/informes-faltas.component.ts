import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Cuadrante } from '../../interfaces/cuadrante';
import { InformesFaltasService } from '../../services/informes-faltas.service';
import { DropdownModule } from 'primeng/dropdown';
import { SelectButtonModule } from "primeng/selectbutton";
import { ChartModule } from 'primeng/chart';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-informes-faltas',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, CommonModule, DropdownModule, SelectButtonModule, ChartModule, CalendarModule, DialogModule],
  templateUrl: './informes-faltas.component.html',
  styleUrl: './informes-faltas.component.scss'
})
export class InformesFaltasComponent implements OnInit {
  tipo: string = 'table';
  tipos: any[] = [];

  date: Date | undefined;

  anyoSeleccionado: number = new Date().getFullYear();
  mostrarAnyo: boolean = true;

  visible: boolean = false;
  faltaSeleccionada: any = null;

  showDialog(cuadrante: any) {
    this.faltaSeleccionada = cuadrante;
    this.visible = true;
  }

  cuadrantes: Cuadrante[] = [];
  cuadrantesFiltrados: Cuadrante[] = [];
  busquedaProfesor: Cuadrante[] = [];

  valorBusqueda: string = '';

  barChartData: any;
  lineChartData: any;
  pieChartData: any;
  barChartOptions: any;
  lineChartOptions: any;
  pieChartOptions: any;
  hayDatos: boolean = false;

  constructor(private informesFaltasService: InformesFaltasService) { }

  ngOnInit(): void {
    this.informesFaltasService.getCuadrantesConFaltas().subscribe(data => {
      this.cuadrantes = data;
      this.filtrarPorMes();

    });

    this.tipos = [
      { name: '', icon: 'pi pi-chart-bar', value: 'bar' },
      { name: '', icon: 'pi pi-chart-line', value: 'line' },
      { name: '', icon: 'pi pi-chart-pie', value: 'pie' },
      { name: '', icon: 'pi pi-table', value: 'table' },
    ];
  }

  buscador(): void {
    const buscar = this.valorBusqueda.toLowerCase().trim();

    if (buscar === '') {
      this.borrarFiltro();
      return;
    }

    this.cuadrantesFiltrados = this.cuadrantes.filter(cuadrante => {
      const nombre = cuadrante.guardia.profesor.nombre?.toLowerCase().includes(buscar) || false;
      const apellidos = cuadrante.guardia.profesor.apellidos?.toLowerCase().includes(buscar) || false;
      const nif = cuadrante.guardia.profesor.nif?.toLowerCase().includes(buscar) || false;
      const email = cuadrante.guardia.profesor.email?.toLowerCase().includes(buscar) || false;
      const telefono = cuadrante.guardia.profesor.telefono?.toString().includes(buscar) || false;

      return nombre || apellidos || nif || email || telefono;
    });
  }

  initializeChartData() {
    const faltasPorMes = new Array(12).fill(0);

    this.cuadrantesFiltrados.forEach(cuadrante => {
      const fecha = new Date(cuadrante.fecha);
      const mes = fecha.getMonth();
      faltasPorMes[mes]++;
    });

    this.updateChartData(faltasPorMes, 'meses');
  }

  cambiarAnyo(opcion: 'anterior' | 'siguiente'): void {
    this.anyoSeleccionado += opcion === 'anterior' ? -1 : 1;
    this.borrarFiltro();
  }

  filtrarPorMes(): void {
    if (this.date) {
      this.valorBusqueda = '';
      this.mostrarAnyo = false;
      const mesSeleccionado = this.date.getMonth();
      const anyoSeleccionado = this.date.getFullYear();

      this.cuadrantesFiltrados = this.cuadrantes.filter(cuadrante => {
        const fecha = new Date(cuadrante.fecha);

        return fecha.getMonth() === mesSeleccionado && fecha.getFullYear() === anyoSeleccionado;
      });

      // Calcular faltas de cada semana del mes para mostrar en los gráficos
      const faltasPorSemana = new Array(4).fill(0);

      this.cuadrantesFiltrados.forEach(cuadrante => {
        const fecha = new Date(cuadrante.fecha);
        const semana = Math.floor((fecha.getDate() - 1) / 7);
        faltasPorSemana[semana]++;
      });
      this.updateChartData(faltasPorSemana, 'semanas');

    } else {
      this.mostrarAnyo = true;
      this.cuadrantesFiltrados = this.cuadrantes.filter(cuadrante => {
        const fecha = new Date(cuadrante.fecha);

        return fecha.getFullYear() === this.anyoSeleccionado;
      });
      this.initializeChartData();
    }
  }

  borrarFiltro(): void {
    this.date = undefined;
    this.mostrarAnyo = true;

    this.cuadrantesFiltrados = this.cuadrantes.filter(cuadrante => {
      const fecha = new Date(cuadrante.fecha);

      return fecha.getFullYear() === this.anyoSeleccionado;
    });
    this.initializeChartData();
  }

  updateChartData(data: number[], tipo: 'meses' | 'semanas') {
    const labels = tipo === 'meses' ?
      ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'] : ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'];

    this.hayDatos = data.some(valor => valor > 0);

    this.barChartData = {
      labels: labels,
      datasets: [{
        label: tipo === 'meses' ? 'Faltas por mes' : 'Faltas por semana',
        data: data,
        backgroundColor: '#42A5F5'
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

    this.lineChartData = {
      labels: labels,
      datasets: [{
        label: tipo === 'meses' ? 'Faltas por mes' : 'Faltas por semana',
        data: data,
        fill: false,
        borderColor: '#42A5F5',
        tension: 0.4
      }]
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

    this.pieChartData = {
      labels: labels,
      datasets: [{
        data: data,
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