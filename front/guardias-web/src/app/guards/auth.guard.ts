import { Inject, inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { GlobalStateService } from '../services/global-state.service';
import { ConfirmationDialogTemplatesService } from '../services/confirmation-dialog-templates.service';

export const authGuard: CanActivateFn = async (route, state) => {
  const authService = inject(AuthService)
  const router = inject(Router)
  const globalStateService = inject(GlobalStateService)
  const confirmDialogTemplatesService = inject(ConfirmationDialogTemplatesService)

  const token = authService.getToken()
  if (token) {
    return true
  } else {
    globalStateService.setConfirmDialog(confirmDialogTemplatesService.loginAlert(() => router.navigate(['login']), () => { }))
    return false
  }
};
