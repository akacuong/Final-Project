import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../common/account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:3306/api/accounts';

  constructor(private http: HttpClient) {}

  getPendingAgents(): Observable<Account[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<Account[]>(`${this.apiUrl}/pending-agents`, { headers });
  }

  approveAgent(agentId: number): Observable<void> {
    const headers = this.createAuthorizationHeader();
    return this.http.post<void>(`${this.apiUrl}/approve-agent`, { agentId }, { headers });
  }

  rejectAgent(agentId: number): Observable<void> {
    const headers = this.createAuthorizationHeader();
    return this.http.post<void>(`${this.apiUrl}/reject-agent`, { agentId }, { headers });
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
}