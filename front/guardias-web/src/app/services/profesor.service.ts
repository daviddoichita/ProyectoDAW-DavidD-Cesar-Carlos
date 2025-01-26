import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProfesorService {
  private apiUrl = 'http://localhost:8000/api/profesores';

  constructor(private http: HttpClient) { }

  findAll(): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  findById(id: number): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin'),
    });
  
    return this.http.get<any>(`${this.apiUrl}/profesores/${id}`, { headers });
  }
  
  save(profesor: any): any {
    const headers = new HttpHeaders({
        'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.post(`${this.apiUrl}/save`, profesor, { headers });
  }

  delete(id: number): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.delete(`${this.apiUrl}/${id}/delete`, { headers });
  }

  update(id: number): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.put<any[]>(`${this.apiUrl}/${id}/update`, { activo: 0 }, { headers });
  }

  update2(id: number, profesor: any): any {
    return this.http.put<any>(`${this.apiUrl}/profesores/${id}`, profesor);
  }
}