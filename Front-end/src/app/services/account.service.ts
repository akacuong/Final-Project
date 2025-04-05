import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../common/account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/api/accounts'; // Đảm bảo rằng URL API là chính xác

  constructor(private http: HttpClient) {}

  getAccountList(): Observable<Account[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Account[]>(`${this.apiUrl}/list`, { headers });
  }

  register(username: string, password: string, email: string, phoneNumber: string, role: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, { 
      username, 
      password, 
      email, 
      phoneNumber, 
      role
    });
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
}