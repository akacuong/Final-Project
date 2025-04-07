import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { JwtHelperService } from './jwthelper.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth'; // URL đăng nhập của bạn

  constructor(
    private http: HttpClient, 
    private router: Router, 
    private jwtHelper: JwtHelperService
  ) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { 
      username, 
      password 
    });
  }

  saveToken(token: string) {
    if (token) {
      localStorage.setItem('token', token);
      console.log('Token saved to localStorage:', token);
    }
  }

  getToken(): string | null {
    try {
      return localStorage.getItem('token');
    } catch (error) {
      console.error('Error accessing token in localStorage', error);
      return null;
    }
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']); // Chuyển hướng đến trang login khi logout
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token && !this.jwtHelper.isTokenExpired(token); // Kiểm tra token có hợp lệ không
  }

  decodeToken(token: string) {
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload)); // Giải mã token thủ công nếu cần
  }

  register(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, { username, password });
  }

  getRoles(): string[] {
    const token = this.getToken();
    if (token) {
      const decodedToken = this.jwtHelper.decodeToken(token); // Sử dụng JwtHelperService để giải mã token
      return decodedToken?.role ? [decodedToken.role] : [];
    }
    return [];
  }
}
