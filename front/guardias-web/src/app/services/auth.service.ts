import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8000'; // Asegúrate de que esta URL sea correcta

  constructor(private http: HttpClient) {}

  login(credentials: { emailOrNif: string, password: string }): Observable<any> {
    return this.http.get(`${this.apiUrl}/login?email=${credentials.emailOrNif}&contraseña=${credentials.password}`);
  }

  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/logout`, {});
  }
}