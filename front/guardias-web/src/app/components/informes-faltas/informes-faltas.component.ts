import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { TableModule } from 'primeng/table';


@Component({
  selector: 'app-informes-faltas',
  standalone: true,
  imports: [HeaderComponent, TableModule],
  templateUrl: './informes-faltas.component.html',
  styleUrl: './informes-faltas.component.scss'
})
export class InformesFaltasComponent {

}
