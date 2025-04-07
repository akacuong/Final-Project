import { Injectable } from '@angular/core';
import { Agent } from '../common/agent';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AgentService {
  private apiUrl = 'http://localhost:8080/agents';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    console.log('Token from localStorage:', token);  // Kiểm tra token lấy được từ localStorage

    if (!token) {
      console.error('Token not found');
      throw new Error('Token not found');
    }

    return new HttpHeaders({
      'Authorization': `Bearer ${token}`  // Gửi token trong header Authorization
    });
  }

  getAgents(): Observable<Agent[]> {
    return this.http.get<Agent[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  addAgent(agent: Agent): Observable<Agent> {
    return this.http.post<Agent>(this.apiUrl, agent, { headers: this.getHeaders() });
  }

  updateAgent(agent: Agent): Observable<Agent> {
    return this.http.put<Agent>(`${this.apiUrl}/${agent.id}`, agent, { headers: this.getHeaders() });
  }

  deleteAgent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }
}
