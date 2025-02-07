import { Component, OnInit, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { RippleModule } from "primeng/ripple";
import { Sidebar, SidebarModule } from "primeng/sidebar";
import { SplitButtonModule } from "primeng/splitbutton";
import { ToolbarModule } from "primeng/toolbar";
import { StyleClassModule } from "primeng/styleclass";
import { AvatarModule } from "primeng/avatar";
import { CommonModule } from "@angular/common";
import { AuthService } from "../../services/auth.service";

@Component({
  selector: "app-header",
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
    CommonModule,
  ],
  templateUrl: "./header.component.html",
  styleUrl: "./header.component.scss",
})
export class HeaderComponent implements OnInit {
  @ViewChild("sidebar") sidebar!: Sidebar;
  menuItems: any[] = [];

  titulo = "HEADER";
  sidebarVisible = false;

  constructor(
    private router: Router,
    private auth: AuthService,
  ) { }

  ngOnInit(): void {
    this.titulo = this.router.url.split("/")[1].toUpperCase().replace("-", " ");
    this.menuItems = [
      {
        routerLink: "/cuadrante",
        icon: "pi pi-home",
        label: "Inicio",
      },
      {
        routerLink: "/nueva-falta",
        icon: "pi pi-calendar-clock",
        label: "Nueva falta",
      },
    ];

    this.auth.getAuthLevel().subscribe({
      next: (isAdmin) => {
        if (isAdmin) {
          this.menuItems.push({
            routerLink: "/subir-sesiones",
            icon: "pi pi-file-import",
            label: "Subir sesiones",
          },
            {
              routerLink: "/informes-faltas",
              icon: "pi pi-chart-bar",
              label: "Informes faltas",
            },
            {
              routerLink: "/informes-asistencias",
              icon: "pi pi-chart-line",
              label: "Informes asistencias",
            },
            {
              routerLink: "/informes-incidencias",
              icon: "pi pi-exclamation-triangle",
              label: "Informes incidencias",
            });
        }
      },
    });
  }

  closeCallback(e: Event): void {
    this.sidebar.close(e);
  }
}
