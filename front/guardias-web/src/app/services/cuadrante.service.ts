import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Cuadrante } from '../interfaces/cuadrante';

@Injectable({
  providedIn: 'root'
})
export class CuadranteService {

  url: string = 'http://localhost:8000/api/cuadrantes'

  headers = () => {
    return new HttpHeaders({
      'Authorization': 'Bearer ' + sessionStorage.getItem('token')
    });
  }

  constructor(private http: HttpClient) { }


  findAll(): Observable<Cuadrante[]> {
    return this.http.get(this.url, { headers: this.headers() }).pipe(
      map((response) => {
        return JSON.parse(JSON.stringify(response)).data;
      })
    );
  }

  findCurrentWeek(): Observable<Cuadrante[]> {
    return this.http.get(this.url + '/currentWeek', { headers: this.headers() }).pipe(
      map((response) => {
        return JSON.parse(JSON.stringify(response)).data;
      })
    );
  }
}
