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
  
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers });
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

  update(id: number, profesor: any): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.put<any>(`${this.apiUrl}/${id}`, profesor, { headers });
  }
}