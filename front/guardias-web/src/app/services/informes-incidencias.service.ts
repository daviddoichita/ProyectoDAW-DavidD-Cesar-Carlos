import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Observer, map } from 'rxjs';
import { Cuadrante } from '../interfaces/cuadrante';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class InformesIncidenciasService {
  private apiUrl = 'https://localhost:8000/api/informes/incidencias';

  constructor(private http: HttpClient, private auth: AuthService) { }

  getCuadrantesConIncidencias(): Observable<Cuadrante[]> {
    return this.http.get<Cuadrante[]>(this.apiUrl, { headers: this.auth.getAuthHeader() });
  }
}
