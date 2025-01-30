import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, map } from "rxjs";
import { Router } from "@angular/router";
import { GlobalStateService } from "./global-state.service";
import { ConfirmationDialogTemplatesService } from "./confirmation-dialog-templates.service";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private apiUrl = "https://localhost:8000/api/auth";

  constructor(
    private http: HttpClient,
    private router: Router,
    private globalStateService: GlobalStateService,
    private confirmationDialogTemplatesService: ConfirmationDialogTemplatesService,
  ) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    const user = JSON.stringify(credentials);
    return this.http.post(`${this.apiUrl}/login`, user, {
      headers: { "Content-Type": "application/json" },
    });
  }

  logout(): Observable<any> {
    return this.http.get(`${this.apiUrl}/logout`, {});
  }

  getToken(): string | null {
    const local = localStorage.getItem("token");
    if (local) {
      return local;
    }
    const session = sessionStorage.getItem("token");
    return session;
  }

  getTokenDate(): string | null {
    const local = localStorage.getItem("tokenDate");
    if (local) {
      return local;
    }
    const session = sessionStorage.getItem("tokenDate");
    return session;
  }

  getAuthHeader(): HttpHeaders {
    const token = this.getToken();
    const tokenDate = this.getTokenDate();
    if (tokenDate) {
      const date = new Date(tokenDate);
      const now = new Date();
      const diff = now.getTime() - date.getTime();
      const diffHours = (diff - 2000) / 1000 / 60 / 60;

      if (diffHours > 24) {
        const clearToken = () => {
          localStorage.removeItem("token");
          sessionStorage.removeItem("token");
          localStorage.removeItem("tokenDate");
          sessionStorage.removeItem("tokenDate");
        };

        this.globalStateService.setConfirmDialog(
          this.confirmationDialogTemplatesService.loginTimeoutAlert(() => {
            this.router.navigate(["/login"]);
            clearToken();
          }, clearToken),
        );

        this.router.navigate([this.router.url]);
      }
    }

    return new HttpHeaders({
      Authorization: "Bearer " + token,
    });
  }

  getAuthLevel(): Observable<boolean> {
    return this.http
      .get<boolean>(this.apiUrl + "Level", {
        headers: this.getAuthHeader(),
      })
      .pipe(
        map((response) => {
          const respJson = JSON.parse(JSON.stringify(response));
          return respJson.authLevel === "ROLE_DIRECCION";
        }),
      );
  }

  isLogged(): boolean {
    const local = localStorage.getItem("token");
    const session = sessionStorage.getItem("token");
    if (!local && !session) {
      return false;
    }
    return true;
  }
}
