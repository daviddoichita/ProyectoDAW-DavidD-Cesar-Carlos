import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { RippleModule } from 'primeng/ripple';
import { Sidebar, SidebarModule } from 'primeng/sidebar';
import { SplitButtonModule } from 'primeng/splitbutton';
import { ToolbarModule } from 'primeng/toolbar';
import { StyleClassModule } from 'primeng/styleclass';
import { AvatarModule } from 'primeng/avatar';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    ToolbarModule,
    ButtonModule,
    SplitButtonModule,
    InputTextModule,
    SidebarModule,
    RippleModule,
    StyleClassModule,
    AvatarModule,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  @ViewChild('sidebar') sidebar!: Sidebar;

  titulo = 'HEADER';
  sidebarVisible = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.titulo = this.router.url.split('/')[1].toUpperCase().replace('-', ' ');
  }

  closeCallback(e: Event): void {
    this.sidebar.close(e);
  }
}
