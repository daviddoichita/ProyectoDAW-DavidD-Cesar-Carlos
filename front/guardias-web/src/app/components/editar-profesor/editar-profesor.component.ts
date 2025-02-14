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

  mostrarErrores: boolean = false;

  errores: Record<string, string | null> = {
    nombre: null,
    apellidos: null,
    contrasenya: null,
    nif: null,
    direccion: null,
    email: null,
    telefono: null,
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
        this.errores['contrasenya'] = value.length >= 6
          ? null
          : 'La contraseña debe tener al menos 6 caracteres.';
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

    console.log('Datos a guardar:', editarProfesor);

    if (this.profesorId) {
      this.profesorService.update(this.profesorId, editarProfesor).
        subscribe({
          next: (_response) => {
            console.log('Actualización exitosa');
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
          },
          error: (err) => {
            console.error('Error al actualizar:', err);
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
        }
      }
    });
  }
}
/*


import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { Router } from '@angular/router';
import { DropdownModule } from 'primeng/dropdown';
import { ProfesorService } from '../../services/profesor.service';
import { MessageModule } from 'primeng/message';
import { CommonModule } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-editar-profesor',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, DropdownModule, MessageModule, CommonModule, ToastModule,
    ConfirmDialogModule, ReactiveFormsModule
  ],
  templateUrl: './editar-profesor.component.html',
  providers: [MessageService, ConfirmDialogModule, ConfirmationService]
})
export class EditarProfesorComponent implements OnInit {

  editarForm: FormGroup;

  profesorId: number | null = null;

  nombre: string = '';
  apellidos: string = '';
  contrasenya: string = '';
  nif: string = '';
  direccion: string = '';
  email: string = '';
  telefono: number = 0;
  sustituye: any = 0;

  mostrarErrores: boolean = false;

  errores: Record<string, string | null> = {
    nombre: null,
    apellidos: null,
    nif: null,
    direccion: null,
    email: null,
    telefono: null,
    sustituye: null
  };

  profesores: { label: string; value: any; }[] = [];

  constructor(
    private profesorService: ProfesorService,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private fb: FormBuilder
  ) {
    this.editarForm = this.fb.group({
      nombre: [
        "",
        [
          Validators.required,
          Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{1,50}$/),
        ],
      ],
      apellidos: [
        "",
        [
          Validators.required,
          Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{1,50}$/),
        ],
      ],
      contrasenya: [
        "",
        [
          Validators.required,
          Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d).+$/),
          Validators.minLength(6)
        ],
      ],
      nif: [
        "",
        [
          Validators.required,
          Validators.pattern(/^[0-9]{8}[A-Za-z]$/),
        ],
      ],
      direccion: [
        "",
        [
          Validators.required,
        ],
      ],
      email: [
        "",
        [
          Validators.required,
          Validators.pattern(/^[^\s@]+@[^\s@]+\.[^\s@]+$/),
        ],
      ],
      telefono: [
        "",
        [
          Validators.required,
          Validators.pattern(/^[0-9]{9}$/),
        ],
      ],
      sustituye: [
        [
          Validators.required,
        ],
      ],
    });
  }

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
    const control = this.editarForm.get(key);
    if (control && control.errors) {
      if (control.errors['required']) {
        return 'Este campo es obligatorio';
      } else if (control.errors['pattern']) {
        switch (key) {
          case 'nombre':
          case 'apellidos':
            return 'Solo se permiten letras y espacios';
          case 'contrasenya':
            return 'La contraseña debe incluir al menos un número y una letra';
          case 'nif':
            return 'Formato de NIF inválido';
          case 'direccion':
            return 'Formato de dirección inválido';
          case 'email':
            return 'Formato de correo electrónico inválido';
          case 'telefono':
            return 'Formato de teléfono inválido';
          default:
            return 'Formato inválido';
        }
      } else if (control.errors['minlength']) {
        return `Debe tener al menos ${control.errors['minlength'].requiredLength} caracteres`;
      }
    }
    return null;
  }

  cargarProfesoresActivos(): void {
    this.profesorService.findAll().subscribe((data: any[]) => {
      this.profesores = data.map(profesor => ({
        label: `${profesor.nombre} ${profesor.apellidos}`,
        value: profesor.id
      }));
      if (this.profesores.length) {
        this.editarForm.patchValue({ sustituye: this.profesores[0].value });
      }
    });
  }

  guardar(): void {
    this.mostrarErrores = true;

    if (this.editarForm.invalid) {
      this.editarForm.markAllAsTouched();
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Por favor, corrige los errores antes de guardar.' });
      return;
    }

    const editarProfesor = {
      nombre: this.editarForm.value.nombre,
      apellidos: this.editarForm.value.apellidos,
      nif: this.editarForm.value.nif,
      direccion: this.editarForm.value.direccion,
      telefono: this.editarForm.value.telefono,
      email: this.editarForm.value.email
    };

    console.log('Datos a guardar:', editarProfesor);

    if (this.profesorId) {
      this.profesorService.update(this.profesorId, editarProfesor).
        subscribe({
          next: (_response) => {
            console.log('Actualización exitosa');
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
          },
          error: (err) => {
            console.error('Error al actualizar:', err);
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
}*/