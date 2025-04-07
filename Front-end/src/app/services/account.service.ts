import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable } from 'rxjs';
import { Account } from '../common/account';
import { tap } from 'rxjs/operators';  // Import tap from rxjs/operators

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/auth/register'; 

  constructor(private http: HttpClient) {}

  getAccountList(): Observable<Account[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Account[]>(`${this.apiUrl}/list`, { headers });
  }

  register(username: string, password: string, email: string, phoneNumber: string, role: string): Observable<any> {
    const body = { username, password, email, phoneNumber, role };
    return this.http.post<any>(this.apiUrl, body);
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
}
