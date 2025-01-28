import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SesionService {
  url: string = 'https://localhost:8000/api/sesiones';

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  load(file: any, year: number): Observable<any> {
    const body = new FormData();
    body.append('file', file)
    body.append('year', year.toString())
    return this.http.post(this.url + '/load', body, { headers: this.auth.getAuthHeader() })
  }
}
