import { Injectable } from '@angular/core';
import { Confirmation } from 'primeng/api';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GlobalStateService {
  private alertMessageSubject = new BehaviorSubject<string | null>(null)
  alertMessage = this.alertMessageSubject.asObservable();

  private confirmDialogSubject = new BehaviorSubject<Confirmation | null>(null)
  confirmDialog = this.confirmDialogSubject.asObservable();

  constructor() { }

  setAlertMessage(message: string) {
    this.alertMessageSubject.next(message)
  }

  clearAlertMessage() {
    this.alertMessageSubject.next(null)
  }

  setConfirmDialog(dialog: Confirmation) {
    this.confirmDialogSubject.next(dialog)
  }

  clearConfirmDialog() {
    this.confirmDialogSubject.next(null)
  }
}
