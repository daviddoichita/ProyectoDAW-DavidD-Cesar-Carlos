import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FaltaService {
  private apiUrl = 'https://localhost:8000/api/faltas';

  constructor(private http: HttpClient, private auth: AuthService) { }

  registrarFalta(falta: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registrar`, falta, { headers: this.auth.getAuthHeader() });
  }
}