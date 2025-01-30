import { Injectable } from '@angular/core';
import { Confirmation } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class ConfirmationDialogTemplatesService {

  constructor() { }

  loginAlert(acceptCallback: () => void, rejectCallback: () => void): Confirmation {
    return {
      target: document.body as EventTarget,
      message: 'Necesitas iniciar sesion para acceder a esta pagina ¿Deseas iniciar sesión?',
      header: 'Faltan credenciales de usuario',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: 'none',
      acceptLabel: 'Aceptar',
      rejectIcon: 'none',
      rejectLabel: 'Cancelar',
      rejectButtonStyleClass: 'p-button-danger',
      accept: acceptCallback,
      reject: rejectCallback
    } as Confirmation
  }

  loginTimeoutAlert(acceptCallback: () => void, rejectCallback: () => void): Confirmation {
    return {
      target: document.body as EventTarget,
      message: 'Tu sesion a caducado, debes volver a iniciar sesion',
      header: 'Sesion caducada',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: 'none',
      acceptLabel: 'Aceptar',
      rejectIcon: 'none',
      rejectLabel: 'Cancelar',
      rejectButtonStyleClass: 'p-button-danger',
      accept: acceptCallback,
      reject: rejectCallback
    } as Confirmation
  }
}
