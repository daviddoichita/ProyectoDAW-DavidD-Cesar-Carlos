import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';
import { InputGroupModule } from 'primeng/inputgroup';
import { PasswordModule } from 'primeng/password';
import { DividerModule } from 'primeng/divider';
import { ButtonModule } from 'primeng/button'
import { ButtonGroupModule } from 'primeng/buttongroup'
import { MessagesModule } from 'primeng/messages';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { MessageModule } from 'primeng/message';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, InputTextModule, PasswordModule, CheckboxModule, InputGroupModule, InputGroupAddonModule, DividerModule, ButtonModule, ButtonGroupModule, MessagesModule, MessageModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    this.loginForm = this.fb.group({
      emailOrNif: ['', [Validators.required, this.emailOrNifValidator.bind(this)]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(6),
          Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d).+$/),
        ],
      ],
      rememberMe: [false],
    });
  }

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    console.log('Inicio de sesión exitoso');
    // this.router.navigate(['/']);
  }

  emailOrNifValidator(control: any): { [key: string]: any } | null {
    const value = control.value;
    if (!value) {
      return null;
    }

    const isEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
    const isNif = /^[0-9]{8}[A-Za-z]$/.test(value) && this.validarNIF(value);

    if (isEmail || isNif) {
      return null;
    }

    return { emailOrNifInvalid: true };
  }

  private validarNIF(nif: string): boolean {
    const nifExpresion = /^[0-9]{8}[A-Za-z]$/;
    if (!nifExpresion.test(nif)) {
      return false;
    }

    const numero = parseInt(nif.slice(0, 8), 10);
    const letra = nif.slice(8).toUpperCase();
    const controlLetras = 'TRWAGMYFPDXBNJZSQVHLCKE';
    const letraEsperada = controlLetras[numero % 23];

    return letra === letraEsperada;
  }
}