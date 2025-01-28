import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Dia } from '../interfaces/dia';
import { ApiResponse } from '../interfaces/api-response';

@Injectable({
  providedIn: 'root'
})
export class DiaService {
  url: string = 'https://localhost:8000/api/dias'

  constructor(private http: HttpClient) { }

  findAll(): Observable<Dia[]> {
    return this.http.get<ApiResponse<Dia[]>>(this.url).pipe(
      map((response) => {
        return response.data
      })
    )
  }

  findCurrentWeek(): Observable<Dia[]> {
    return this.http.get<ApiResponse<Dia[]>>(this.url + '/currentWeek').pipe(
      map((response) => {
        return response.data
      })
    )
  }
}
