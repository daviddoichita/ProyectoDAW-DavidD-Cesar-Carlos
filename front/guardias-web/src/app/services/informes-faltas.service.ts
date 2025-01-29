import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class InformesFaltasService {
    private apiUrl = 'http://localhost:8000/api/informes/faltas';

    constructor(private http: HttpClient) { }

}
