import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { DropdownModule } from 'primeng/dropdown';
import { ProfesorService } from '../../services/profesor.service';
import { MessageModule } from 'primeng/message';
import { CommonModule } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { Profesor } from '../../interfaces/profesor';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-alta-profesor',
  standalone: true,
  imports: [
    HeaderComponent,
    TableModule,
    ButtonModule,
    InputTextModule,
    FormsModule,
    DropdownModule,
    MessageModule,
    CommonModule,
    ToastModule,
    ConfirmDialogModule,
    ReactiveFormsModule,
  ],
  templateUrl: './alta-profesor.component.html',
  providers: [MessageService, ConfirmDialogModule, ConfirmationService],
})
export class AltaProfesorComponent implements OnInit {
  altaForm: FormGroup;

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
    contrasenya: null,
    nif: null,
    direccion: null,
    email: null,
    telefono: null,
    sustituye: null,
  };

  profesores: { label: string; value: any; }[] = [];

  constructor(
    private profesorService: ProfesorService,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private fb: FormBuilder
  ) {
    this.altaForm = this.fb.group({
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
        null,
        [
          Validators.required,
        ],
      ],
    });
  }

  ngOnInit() {
    this.cargarProfesoresActivos();
  }

  getErrores(key: string): string | null {
    const control = this.altaForm.get(key);
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
          case 'sustituye':
            return 'Debes seleccionar un profesor';
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
      this.profesores = data.map((profesor) => ({
        label: `${profesor.nombre} ${profesor.apellidos}`,
        value: profesor.id,
      }));
      if (this.profesores.length) {
        this.altaForm.patchValue({ sustituye: null });
      }
    });
  }

  guardar(): void {
    this.mostrarErrores = true;

    if (this.altaForm.invalid) {
      this.altaForm.markAllAsTouched();
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Por favor, corrige los errores antes de guardar.',
      });
      return;
    }

    const profesor: Profesor = {
      nombre: this.altaForm.value.nombre,
      apellidos: this.altaForm.value.apellidos,
      nif: this.altaForm.value.nif,
      direccion: this.altaForm.value.direccion,
      telefono: this.altaForm.value.telefono,
      email: this.altaForm.value.email,
    };
    this.sustituye = this.altaForm.get('sustituye')?.value;

    this.confirmationService.confirm({
      message: '¿Estás seguro de sustituir este profesor?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Aceptar',
      rejectLabel: 'Cancelar',
      accept: () => {
        this.profesorService.save(profesor, this.sustituye.value).subscribe({
          next: (_response: any) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Profesor guardado y sesiones reasignadas.',
            });
            setTimeout(() => {
              this.router.navigate(['/listado-profesores']);
            }, 1000);
          },
          error: (error) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'No se pudo guardar el profesor.',
            });
            console.error(error);
          },
        });
      },
      reject: () => {
        this.messageService.add({
          severity: 'info',
          summary: 'Cancelado',
          detail: 'No se realizaron cambios.',
        });
      },
    });
  }

  cancelar(): void {
    this.confirmationService.confirm({
      message: '¿Estas seguro que quieres cancelarlo?',
      header: 'Confirmacion',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: 'none',
      acceptLabel: 'Aceptar',
      rejectLabel: 'Cancelar',
      rejectIcon: 'none',
      rejectButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.messageService.add({
          severity: 'info',
          summary: 'Confirmado',
          detail: 'Se han cancelado los cambios',
        });
        setTimeout(() => {
          this.router.navigate(['/listado-profesores']);
        }, 1000);
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Cancelado',
          detail: 'Cancelado',
        });
      },
    });
  }
}
