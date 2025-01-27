import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Intervalo } from '../interfaces/intervalo';
import { AuthService } from './auth.service';
import { ApiResponse } from '../interfaces/api-response';

@Injectable({
  providedIn: 'root'
})
export class IntervalosService {

  url: string = "https://localhost:8000/api/intervalos";

  constructor(private http: HttpClient, private auth: AuthService) { }

  findAll(): Observable<Intervalo[]> {
    return this.http.get<ApiResponse<Intervalo[]>>(this.url).pipe(
      map((response) => response.data)
    )
  }
}
