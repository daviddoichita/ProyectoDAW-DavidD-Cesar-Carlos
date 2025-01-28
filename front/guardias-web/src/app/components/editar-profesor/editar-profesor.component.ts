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

@Component({
  selector: 'app-editar-profesor',
  standalone: true,
  imports: [HeaderComponent, TableModule, ButtonModule, InputTextModule, FormsModule, DropdownModule, MessageModule,
    CommonModule],
  templateUrl: './editar-profesor.component.html',
})
export class AltaProfesorComponent implements OnInit {
  nombre: string = '';
  apellidos: string = '';
  contrasenya: string = '';
  nif: string = '';
  direccion: string = '';
  email: string = '';
  telefono: string = '';
  sustituye: any = null;

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

  constructor(private profesorService: ProfesorService, private router: Router) { }

  ngOnInit() {
    this.cargarProfesoresActivos();
  }

  getErrores(key: string): string | null {
    return this.errores[key] || null;
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

  validarCampo(campo: keyof AltaProfesorComponent): void {
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
      case 'sustituye':
        this.errores['sustituye'] = this.sustituye && this.sustituye.value
          ? null
          : 'Selecciona un profesor para sustituir.';
        break;
    }
  }

  validarTodo(): boolean {
    Object.keys(this.errores).forEach(field => this.validarCampo(field as keyof AltaProfesorComponent));
    return !Object.values(this.errores).some(error => error !== null);
  }

  guardar(): void {
    if (!this.validarTodo()) {
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
      sustituyeId: this.sustituye?.value
    };

    this.profesorService.save(editarProfesor).subscribe(() => {
      this.router.navigate(['/listado-profesores']);
    });
  }

  cancelar(): void {
    this.router.navigate(['/listado-profesores']);
  }

  private cargarDatosProfesor(): void {
    this.profesorService.findById(this.profesorId).subscribe({
      next: (profesor: { [key: string]: any; }) => {
        this.profesorForm.patchValue(profesor);
      },
    });
  }

  onSubmit(): void {
    if (this.profesorForm.valid) {
      this.profesorService.update2(this.profesorId, this.profesorForm.value).subscribe({
        next: () => {
          this.router.navigate(['/listado-profesores']);
        }
      });
    }
  }
}