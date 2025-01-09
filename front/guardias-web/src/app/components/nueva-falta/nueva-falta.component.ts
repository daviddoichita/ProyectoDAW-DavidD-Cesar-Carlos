import { Component } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FloatLabelModule } from 'primeng/floatlabel'
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-nueva-falta',
  standalone: true,
  imports: [DropdownModule, CalendarModule, CommonModule, FormsModule, FloatLabelModule, TableModule, ButtonModule],
  templateUrl: './nueva-falta.component.html',
  styleUrl: './nueva-falta.component.scss'
})
export class NuevaFaltaComponent {
  rangeDates: Date[] | undefined;

}