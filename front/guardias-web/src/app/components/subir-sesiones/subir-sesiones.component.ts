import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FileUploadHandlerEvent, FileUploadModule, UploadEvent } from 'primeng/fileupload';
import { CommonModule } from '@angular/common';
import { SesionService } from '../../services/sesion.service';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-subir-sesiones',
  standalone: true,
  imports: [HeaderComponent, FileUploadModule, CommonModule, ProgressSpinnerModule, ToastModule],
  templateUrl: './subir-sesiones.component.html',
  styleUrl: './subir-sesiones.component.scss',
  providers: [MessageService]
})
export class SubirSesionesComponent {
  uploadedFiles: any[] = [];
  uploading: boolean = false;

  constructor(
    private sesionService: SesionService,
    private messageService: MessageService
  ) { }

  upload(event: FileUploadHandlerEvent) {
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth();
    const currentDate = new Date().getDate();
    const year = (currentMonth < 9 || (currentMonth === 9 && currentDate < 10)) ? currentYear - 1 : currentYear

    this.uploading = true
    this.sesionService.load(event.files[0], year).subscribe({
      next: (response) => {
        console.log(response)
        this.uploading = false
        this.messageService.add({ severity: 'success', summary: 'Sesiones subidas', detail: 'Subida correcta' })
      }, error: (error) => {
        console.error(error)
        this.uploading = false
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Ha ocurrido un error al subir las sesiones' })
      }
    })
  }
}
