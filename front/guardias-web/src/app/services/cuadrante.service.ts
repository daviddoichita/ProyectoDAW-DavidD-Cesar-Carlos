import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Observer, map } from 'rxjs';
import { Cuadrante } from '../interfaces/cuadrante';
import { AuthService } from './auth.service';
import { ApiResponse } from '../interfaces/api-response';

@Injectable({
  providedIn: 'root'
})
export class CuadranteService {

  url: string = 'https://localhost:8000/api/cuadrantes'

  constructor(private http: HttpClient, private auth: AuthService) { }


  findAll(): Observable<Cuadrante[]> {
    return this.http.get<ApiResponse<Cuadrante[]>>(this.url, { headers: this.auth.getAuthHeader() }).pipe(
      map((response) => response.data)
    )
  }

  findCurrentWeek(): Observable<Cuadrante[]> {
    return this.http.get<ApiResponse<Cuadrante[]>>(this.url + '/currentWeek').pipe(
      map((response) => response.data)
    )
  }

  findToday(): Observable<Cuadrante[]> {
    return this.http.get<ApiResponse<Cuadrante[]>>(this.url + '/today').pipe(
      map((response) => response.data)
    )
  }
}
