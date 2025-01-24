import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Intervalo } from '../interfaces/intervalo';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class IntervalosService {

  url: string = "http://localhost:8000/api/intervalos";

  constructor(private http: HttpClient, private auth: AuthService) { }

  findAll(): Observable<Intervalo[]> {
    return this.http.get(this.url, { headers: this.auth.getAuthHeader() }).pipe(
      map((response) => {
        return JSON.parse(JSON.stringify(response)).data
      })
    )
  }
}
