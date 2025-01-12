import { Component, OnInit } from '@angular/core';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { SplitButtonModule } from 'primeng/splitbutton';
import { InputTextModule } from 'primeng/inputtext';
import { SidebarModule } from 'primeng/sidebar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    ToolbarModule,
    ButtonModule,
    SplitButtonModule,
    InputTextModule,
    SidebarModule,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  titulo = 'HEADER';
  sidebarVisible = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.titulo = this.router.url.split('/')[1].toUpperCase();
  }
}
