import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProfesorService {
  private apiUrl = 'https://localhost:8000/api/profesores';

  constructor(private http: HttpClient, private auth: AuthService) { }

  findAll(): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.get<any[]>(this.apiUrl, { headers: this.auth.getAuthHeader() });
  }

  findById(id: number): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin'),
    });

    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers: this.auth.getAuthHeader() });
  }

  save(profesor: any): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.post(`${this.apiUrl}/save`, profesor, { headers: this.auth.getAuthHeader() });
  }

  delete(id: number): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.get(`${this.apiUrl}/${id}/delete`, { headers: this.auth.getAuthHeader() });
  }

  update(id: number, profesor: any): any {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('admin:admin')
    });

    return this.http.put<any>(`${this.apiUrl}/${id}`, profesor, { headers: this.auth.getAuthHeader() });
  }
}