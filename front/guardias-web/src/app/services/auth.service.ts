import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8000/api/auth';

  constructor(private http: HttpClient) { }

  login(credentials: { email: string, password: string }): Observable<any> {
    const user = JSON.stringify(credentials);
    return this.http.post(`${this.apiUrl}/login`, user, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  logout(): Observable<any> {
    return this.http.get(`${this.apiUrl}/logout`, {});
  }

  getToken(): string | null {
    const local = localStorage.getItem('token')
    if (local) {
      return local
    }
    const session = sessionStorage.getItem('token')
    return session
  }

  getAuthHeader() {
    return new HttpHeaders({
      'Authorization': 'Bearer ' + this.getToken()
    });
  }


  isLogged(): boolean {
    const local = localStorage.getItem('token')
    const session = sessionStorage.getItem('token')
    if (!local && !session) {
      return false
    }
    return true
  }
}
