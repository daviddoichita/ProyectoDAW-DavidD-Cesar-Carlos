import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DropdownModule } from 'primeng/dropdown';
import { ProfesorService } from '../../services/profesor.service';
import { MessageModule } from 'primeng/message';
import { CommonModule } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

@Component({
  selector: 'app-editar-profesor',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, DropdownModule, MessageModule, CommonModule, ToastModule,
    ConfirmDialogModule
  ],
  templateUrl: './editar-profesor.component.html',
  providers: [MessageService, ConfirmDialogModule, ConfirmationService]
})
export class EditarProfesorComponent implements OnInit {

  profesorId: number | null = null;

  nombre: string = '';
  apellidos: string = '';
  contrasenya: string = '';
  nif: string = '';
  direccion: string = '';
  email: string = '';
  telefono: string = '';
  sustituye: any = null;

  mostrarErrores: boolean = false;

  errores: Record<string, string | null> = {
    nombre: null,
    apellidos: null,
    contrasenya: null,
    nif: null,
    direccion: null,
    email: null,
    telefono: null,
    sustituye: null
  };

  profesores: { label: string; value: any; }[] = [];

  constructor(private profesorService: ProfesorService, private router: Router, private messageService: MessageService, private confirmationService: ConfirmationService
  ) { }

  ngOnInit() {
    this.profesorId = this.getProfesorIdFromRoute();
    if (this.profesorId) {
      this.cargarDatosProfesor();
    }
    this.cargarProfesoresActivos();
  }

  private getProfesorIdFromRoute(): number | null {
    const id = window.location.pathname.split('/').pop();
    return id ? parseInt(id, 10) : null;
  }

  getErrores(key: string): string | null {
    return this.mostrarErrores ? this.errores[key] : null;
  }

  cargarProfesoresActivos(): void {
    this.profesorService.findAll().subscribe((data: any[]) => {
      this.profesores = data.map(profesor => ({
        label: `${profesor.nombre} ${profesor.apellidos}`,
        value: profesor.id
      }));
      this.sustituye = this.profesores.length ? this.profesores[0].value : null;
    });
  }

  validarCampo(campo: keyof EditarProfesorComponent): void {
    const value = this[campo] as string;

    switch (campo) {
      case 'nombre':
        this.errores['nombre'] = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{1,50}$/.test(value)
          ? null
          : 'El nombre debe contener solo letras y tener entre 1 y 50 caracteres.';
        break;
      case 'apellidos':
        this.errores['apellidos'] = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{1,50}$/.test(value)
          ? null
          : 'Los apellidos deben contener solo letras y tener entre 1 y 50 caracteres.';
        break;
      case 'contrasenya':
        this.errores['contrasenya'] = /^(?=.*\d).{6,}$/.test(value)
          ? null
          : 'La contraseña debe tener al menos 6 caracteres y contener al menos un número.';
        break;
      case 'nif':
        this.errores['nif'] = /^[0-9]{8}[A-Za-z]$/.test(value)
          ? null
          : 'El NIF debe tener 8 números seguidos de una letra.';
        break;
      case 'direccion':
        this.errores['direccion'] = value.length > 0
          ? null
          : 'La dirección es obligatoria.';
        break;
      case 'email':
        this.errores['email'] = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)
          ? null
          : 'Introduce un correo electrónico válido.';
        break;
      case 'telefono':
        this.errores['telefono'] = /^[0-9]{9}$/.test(value)
          ? null
          : 'El teléfono debe tener 9 dígitos.';
        break;
    }
  }

  validarTodo(): boolean {
    Object.keys(this.errores).forEach(field => this.validarCampo(field as keyof EditarProfesorComponent));
    return !Object.values(this.errores).some(error => error !== null);
  }

  guardar(): void {
    this.mostrarErrores = true;
    if (!this.validarTodo()) {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Por favor, corrige los errores antes de guardar.' });
      return;
    }

    const editarProfesor = {
      nombre: this.nombre,
      apellidos: this.apellidos,
      contrasenya: this.contrasenya,
      nif: this.nif,
      direccion: this.direccion,
      email: this.email,
      telefono: this.telefono,
    };

    if (this.profesorId) {
      this.profesorService.update(this.profesorId, editarProfesor).
        subscribe({
          next: (_response) => {
            this.confirmationService.confirm({
              message: '¿Estas seguro que quieres guardarlo?',
              header: 'Confirmacion',
              icon: 'pi pi-exclamation-triangle',
              acceptIcon: "none",
              acceptLabel: "Aceptar",
              rejectLabel: "Cancelar",
              rejectIcon: "none",
              rejectButtonStyleClass: "p-button-danger",
              accept: () => {
                this.messageService.add({ severity: 'info', summary: 'Confirmado', detail: 'Se han guardado los cambios' })
                setTimeout(() => {
                  this.router.navigate(['/listado-profesores'])
                }, 1000)
              },
              reject: () => {
                this.messageService.add({ severity: 'error', summary: 'Cancelado', detail: 'Cancelado' })
              }
            })
          }
        })
    }
  }

  cancelar(): void {
    this.confirmationService.confirm({
      message: '¿Estas seguro que quieres cancelarlo?',
      header: 'Confirmacion',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: "none",
      acceptLabel: "Aceptar",
      rejectLabel: "Cancelar",
      rejectIcon: "none",
      rejectButtonStyleClass: "p-button-danger",
      accept: () => {
        this.messageService.add({ severity: 'info', summary: 'Confirmado', detail: 'Se han cancelado los cambios' })
        setTimeout(() => {
          this.router.navigate(['/listado-profesores'])
        }, 1000)
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Cancelado', detail: 'Cancelado' })
      }
    })
  }


  private cargarDatosProfesor(): void {
    if (!this.profesorId) return;

    this.profesorService.findById(this.profesorId).subscribe({
      next: (profesor: any) => {
        if (profesor) {
          this.nombre = profesor.nombre || '';
          this.apellidos = profesor.apellidos || '';
          this.contrasenya = profesor.contrasenya || '';
          this.nif = profesor.nif || '';
          this.direccion = profesor.direccion || '';
          this.email = profesor.email || '';
          this.telefono = profesor.telefono || '';
          this.sustituye = profesor.sustituye || null;
        }
      }
    });
  }
}