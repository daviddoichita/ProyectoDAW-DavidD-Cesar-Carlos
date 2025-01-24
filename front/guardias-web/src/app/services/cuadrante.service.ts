import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Cuadrante } from '../interfaces/cuadrante';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CuadranteService {

  url: string = 'http://localhost:8000/api/cuadrantes'

  constructor(private http: HttpClient, private auth: AuthService) { }


  findAll(): Observable<Cuadrante[]> {
    return this.http.get(this.url, { headers: this.auth.getAuthHeader() }).pipe(
      map((response) => {
        return JSON.parse(JSON.stringify(response)).data;
      })
    );
  }

  findCurrentWeek(): Observable<Cuadrante[]> {
    return this.http.get(this.url + '/currentWeek', { headers: this.auth.getAuthHeader() }).pipe(
      map((response) => {
        return JSON.parse(JSON.stringify(response)).data;
      })
    );
  }
}
