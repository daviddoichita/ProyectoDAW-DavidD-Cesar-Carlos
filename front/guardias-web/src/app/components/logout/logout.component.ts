import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.scss'
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('tokenDate');
    localStorage.removeItem('token');
    localStorage.removeItem('tokenDate');
    this.router.navigate(['login']);
  }
}
