import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Profesor } from './profesor';

@Injectable({
    providedIn: 'root'
})
export class ProfesorService {
    private apiUrl = 'http://localhost:8000/api/profesores';

    constructor(private http: HttpClient) { }

    findAll(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl);
    }

    findById(id: number): Observable<Profesor> {
        return this.http.get<Profesor>(`${this.apiUrl}/${id}`);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}/delete`);
    }

    save(profesor: Profesor): Observable<Profesor> {
        return this.http.post<Profesor>(this.apiUrl, profesor);
    }

    /*save2(profesor: Profesor): Observable<Profesor> {
        return this.http.post<Profesor>(`${this.apiUrl}/${id}/save`);
    }*/
}