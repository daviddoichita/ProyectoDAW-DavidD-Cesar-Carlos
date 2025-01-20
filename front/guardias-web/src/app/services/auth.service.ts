import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8000';

  constructor(private http: HttpClient) {}

  login(credentials: { emailOrNif: string, password: string }): Observable<any> {
    const params = new HttpParams()
      .set('usuario', credentials.emailOrNif)
      .set('contrasenya', credentials.password);
    return this.http.get(`${this.apiUrl}/login`, { params });
  }

  logout(): Observable<any> {
    return this.http.get(`${this.apiUrl}/logout`, {});
  }
}