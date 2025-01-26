import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { ActivatedRoute, Router } from '@angular/router';
import { ProfesorService } from '../../services/profesor.service';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-editar-profesor',
  standalone: true,
  imports: [HeaderComponent, ReactiveFormsModule],
  templateUrl: './editar-profesor.component.html',
})
export class EditarProfesorComponent implements OnInit {
  profesorForm!: FormGroup;
  profesorId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private profesorService: ProfesorService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.profesorId = +idParam;
    } else {
      console.error('No se proporcionó un ID válido en la URL');
      this.router.navigate(['/listado-profesores']);
      return;
    }

    this.initForm();
    this.cargarDatosProfesor();
  }

  private initForm(): void {
    this.profesorForm = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      nif: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
    });
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
