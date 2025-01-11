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

import { InputGroupAddonModule } from 'primeng/inputgroupaddon';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, InputTextModule, PasswordModule, CheckboxModule, InputGroupModule, InputGroupAddonModule, DividerModule, ButtonModule, ButtonGroupModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';

  constructor(
    private router: Router,
  ) {}

  login(): void {
    if (!this.validarEmail(this.email)) {
      this.error = 'Formato de email incorrecto';
      return;
    }
    if (!this.validarPassword(this.password)) {
      this.error =
        'La contraseña debe tener más de 6 caracteres y al menos un número y una letra';
      return;
    }
  }

  validarEmail(email: string): boolean {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  }

  validarPassword(password: string): boolean {
    const re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
    return re.test(password);
  }
}
