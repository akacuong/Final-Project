import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { JwtHelperService } from './jwthelper.service';  
import { jwtDecode }  from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth'; 

  constructor(
    private http: HttpClient, 
    private router: Router, 
    private jwtHelper: JwtHelperService // Sử dụng JwtHelperService để giải mã token
  ) { }

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
    return !!token && !this.jwtHelper.isTokenExpired(token); // Kiểm tra token có hợp lệ không
  }
  decodeToken(token: string) {
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload));
  }
  register(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, { username, password });
  }
  
  getRoles(): string[] {
    const token = this.getToken();
    if (token) {
      const decodedToken = this.jwtHelper.decodeToken(token); // Sử dụng JwtHelperService để giải mã
      return decodedToken?.role ? [decodedToken.role] : []; // Trả về role từ payload
    }
    return [];
  }
}
