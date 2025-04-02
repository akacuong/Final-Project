import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgentService } from '../../services/agent.service';
import { Agent } from '../../common/agent';
import { Router, RouterModule } from '@angular/router';
import { LoyaltyService } from '../../services/loyalty.service';

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
  phoneNumber: string = '';
  name: string = '';
  shopName: string = '';
  showWarning: boolean = false;  
  logoPath = 'assets/logo.jpg';
  username: string | null = '';

  constructor(
    private agentService: AgentService, 
    private router: Router,
    private loyaltyService: LoyaltyService
  ) {}

  ngOnInit(): void {
    this.loadAgents();
    this.loyaltyPoints = this.loyaltyService.getCustomerPoints();
    this.username = localStorage.getItem('loggedInUser');
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
    if (this.name && this.phoneNumber && this.searchQuery) {
      this.router.navigate(['customer/booking']);
    } else {
      this.showWarning = true;
    }
  }
}
