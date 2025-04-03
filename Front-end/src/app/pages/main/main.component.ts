import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgentService } from '../../services/agent.service';
import { Agent } from '../../common/agent';
import { Router, RouterModule } from '@angular/router';
import { LoyaltyService } from '../../services/loyalty.service';
import { Account } from '../../common/account';

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

  constructor(
    private agentService: AgentService, 
    private router: Router,
    private loyaltyService: LoyaltyService
  ) {}

  ngOnInit(): void {
    this.loadAgents();
    this.loyaltyPoints = this.loyaltyService.getCustomerPoints();
    this.getUserInfo(); // Hàm lấy username
    // Lấy username và account từ localStorage
    this.username = localStorage.getItem('loggedInUser');  // Lấy tên người dùng từ localStorage
    this.loggedInAccount = JSON.parse(localStorage.getItem('loggedInAccount') || '{}');  // Lấy thông tin tài khoản
    console.log('Username from localStorage:', this.username); // Kiểm tra trong console
  }
  

  loadAgents(): void {
    this.agents = this.agentService.getAgents();
    this.filteredAgents = [...this.agents];
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
    this.username = localStorage.getItem('loggedInUser');  
    this.loggedInAccount = JSON.parse(localStorage.getItem('loggedInAccount') || '{}');
  }
  logout(): void {
    localStorage.removeItem('loggedInUser');  
    localStorage.removeItem('loggedInAccount');  
    this.username = null;
    this.loggedInAccount = null;
    this.router.navigate(['/']).then(() => {
      window.location.reload(); 
    });
  }
  
}
