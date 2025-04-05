import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgentService } from '../../services/agent.service';
import { Agent } from '../../common/agent';
import { Router, RouterModule } from '@angular/router';
import { LoyaltyService } from '../../services/loyalty.service';
import { Account } from '../../common/account';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  agents: Agent[] = [];
  filteredAgents: Agent[] = [];
  loyaltyPoints: number = 0;
  searchQuery: string = ''; 
  selectedAgent: Agent | null = null;
  shopName: string = '';
  showWarning: boolean = false;  
  logoPath = 'assets/logo.jpg';
  username: string | null = '';
  loggedInAccount: Account | null = null;
  private apiUrl = 'https://your-backend-api-url/customers';

  constructor(
    private agentService: AgentService, 
    private router: Router,
    private loyaltyService: LoyaltyService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadAgents();
    this.loyaltyPoints = this.loyaltyService.getCustomerPoints();
    this.getUserInfo(); // Hàm lấy username
  }

  loadAgents(): void {
    this.agentService.getAgents().subscribe(data => {
      this.agents = data;
      this.filteredAgents = [...this.agents];
    });
  }

  searchAgent(): void {
    if (!this.searchQuery.trim()) {
      this.filteredAgents = [...this.agents];
      return;
    }
    this.filteredAgents = this.agents.filter(agent =>
      agent.id.toString().includes(this.searchQuery) ||
      agent.agentName.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  selectAgent(agent: Agent) {
    console.log("Selected Agent: ", agent);
    this.selectedAgent = agent;
  }

  scheduleAppointment() {
    if (this.searchQuery) {
      this.router.navigate(['customer/booking']);
    } else {
      this.showWarning = true;
    }
  }

  navigateToCustomerInfo(): void {
    if (this.username) {
      this.router.navigate(['customer/customer-infor']);
    }
  }

  getUserInfo(): void {
    this.http.get<{ username: string, account: Account }>(`${this.apiUrl}/loggedInUser`)
      .subscribe(data => {
        this.username = data?.username || null;
        this.loggedInAccount = data?.account || null;
      });
  }

  logout(): void {
    this.http.post(`${this.apiUrl}/logout`, {})
      .subscribe(() => {
        this.username = null;
        this.loggedInAccount = null;
        this.router.navigate(['/']).then(() => {
          window.location.reload();
        });
      });
  }
}