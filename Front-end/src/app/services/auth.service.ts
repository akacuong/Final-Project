import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth'; 

  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { 
      username, 
      password 
    });
  }
  
  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']); 
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token;
  }

  getRoles(): string[] {
    // Giả sử token chứa thông tin roles
    const token = this.getToken();
    if (token) {
      // Giải mã token để lấy thông tin roles
      const decodedToken = JSON.parse(atob(token.split('.')[1]));
      return decodedToken.roles || [];
    }
    return [];
  }
}