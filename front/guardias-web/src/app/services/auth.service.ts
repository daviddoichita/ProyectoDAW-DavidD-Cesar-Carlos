import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8000/api/profesores';

  constructor(private http: HttpClient) {}

  login(credentials: { emailOrNif: string, password: string }): Observable<any> {
    const params = new HttpParams()
      .set('email', credentials.emailOrNif)
      .set('contrasenya', credentials.password);
    return this.http.post(`${this.apiUrl}/login`, null, { params });
  }

  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/logout`, {});
  }
}