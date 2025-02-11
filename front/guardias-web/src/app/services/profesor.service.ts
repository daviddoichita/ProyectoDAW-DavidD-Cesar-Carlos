import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { catchError, Observable, throwError } from 'rxjs';
import { map } from 'rxjs';
import { Profesor } from '../interfaces/profesor';

@Injectable({
  providedIn: 'root'
})
export class ProfesorService {
  private apiUrl = 'https://localhost:8000/api/profesores';

  constructor(private http: HttpClient, private auth: AuthService) { }

  findAll(): any {
    return this.http.get<any[]>(this.apiUrl, { headers: this.auth.getAuthHeader() });
  }

  findById(id: number): Observable<any> {

    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.auth.getAuthHeader() });
  }

  save(profesor: any): Observable<any> {
    const data = new FormData();
    data.append('profesorDTO', JSON.stringify(profesor));
    return this.http.post<any>(`${this.apiUrl}/save`, profesor, { headers: this.auth.getAuthHeader() });
  }

  delete(id: number): Observable<any> {

    return this.http.get<any>(`${this.apiUrl}/${id}/delete`, { headers: this.auth.getAuthHeader() });
  }

  update(id: number, profesor: any): Observable<any> {
    console.log('Enviando solicitud PUT a:', `${this.apiUrl}/${id}`);
    console.log('Datos del profesor:', profesor);

    return this.http.put<any>(`${this.apiUrl}/${id}`, profesor, { headers: this.auth.getAuthHeader() }).pipe(
      map(response => {
        return response;
      })
    );
  }

}
