import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { InputTextModule } from "primeng/inputtext";
import { CheckboxModule } from "primeng/checkbox";
import { InputGroupModule } from "primeng/inputgroup";
import { PasswordModule } from "primeng/password";
import { DividerModule } from "primeng/divider";
import { ButtonModule } from "primeng/button";
import { ButtonGroupModule } from "primeng/buttongroup";
import { MessagesModule } from "primeng/messages";
import { InputGroupAddonModule } from "primeng/inputgroupaddon";
import { MessageModule } from "primeng/message";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { RippleModule } from "primeng/ripple";
import { AuthService } from "../../services/auth.service";
import { HttpClientModule } from "@angular/common/http";
import { GlobalStateService } from "../../services/global-state.service";
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    CheckboxModule,
    InputGroupModule,
    InputGroupAddonModule,
    DividerModule,
    ButtonModule,
    RippleModule,
    ButtonGroupModule,
    MessagesModule,
    MessageModule,
    RouterModule,
    DialogModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  credentialsError: boolean = false;

  visible: boolean = false;

  showDialog() {
    this.visible = true;
  }

  hideDialog() {
    this.visible = false;
  }

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private globalStateService: GlobalStateService
  ) {
    this.loginForm = this.fb.group({
      emailOrNif: [
        '',
        [Validators.required, this.emailOrNifValidator.bind(this)],
      ],
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

  ngOnInit(): void {
    const session = sessionStorage.getItem('token');
    const local = localStorage.getItem('token');
    if (session || local) {
      this.router.navigate(['cuadrante']);
    }

    this.loginForm.get('emailOrNif')?.valueChanges.subscribe(() => {
      this.credentialsError = false;
    });

    this.loginForm.get('password')?.valueChanges.subscribe(() => {
      this.credentialsError = false;
    });
  }

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.credentialsError = false;
    let user: { email?: string; nif?: string; password: string } = {
      password: '',
    };

    if (this.isEmail(this.loginForm.value.emailOrNif)) {
      user = {
        email: this.loginForm.value.emailOrNif,
        password: this.loginForm.value.password,
      };
    } else {
      user = {
        nif: this.loginForm.value.emailOrNif,
        password: this.loginForm.value.password,
      };
    }
    this.authService.login(user).subscribe({
      next: (response) => {
        if (this.loginForm.value.rememberMe) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('tokenDate', new Date().toISOString());
        } else {
          sessionStorage.setItem('token', response.token);
          sessionStorage.setItem('tokenDate', new Date().toISOString());
        }
        this.globalStateService.alertMessage.subscribe({
          next: (route) => {
            if (route) {
              this.globalStateService.clearAlertMessage();
              this.router.navigate([route]);
            } else {
              this.router.navigate(['cuadrante']);
            }
          },
        });
      },
      error: (error) => {
        console.error('Error en el inicio de sesión:', error);

        if (
          error.status === 403 ||
          error.status === 401 ||
          error.status === 404
        ) {
          this.credentialsError = true;
          console.error('Credenciales incorrectas');
        } else {
          console.error('Error');
        }
      },
    });
  }

  isEmail(value: string) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
  }

  isNif(value: string) {
    return /^[0-9]{8}[A-Za-z]$/.test(value) && this.validarNIF(value);
  }

  emailOrNifValidator(control: any): { [key: string]: any } | null {
    const value = control.value;
    if (!value) {
      return null;
    }

    const isEmail = this.isEmail(value);
    const isNif = this.isNif(value);

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
