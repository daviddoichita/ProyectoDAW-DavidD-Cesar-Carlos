import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Observer, map } from 'rxjs';
import { Cuadrante } from '../interfaces/cuadrante';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class InformesFaltasService {
    private apiUrl = 'https://localhost:8000/api/informes/faltas';

    constructor(private http: HttpClient, private auth: AuthService) { }

    getCuadrantesConFaltas(): Observable<Cuadrante[]> {
        return this.http.get<Cuadrante[]>(this.apiUrl, { headers: this.auth.getAuthHeader() });
    }
}
