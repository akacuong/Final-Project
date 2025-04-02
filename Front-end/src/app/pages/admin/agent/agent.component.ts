import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Agent } from '../../../common/agent';
import { AgentService } from '../../../services/agent.service';

@Component({
  selector: 'app-agent',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agent.component.html',
  styleUrls: ['./agent.component.css']
})
export class AgentComponent implements OnInit {
  agents: Agent[] = [];
  newAgent: Agent = new Agent(0, '', '', '', '', '', '', '', '', 0);
  selectedAgent: Agent | null = null;

  constructor(private agentService: AgentService) {}

  ngOnInit(): void {
    this.loadAgents();
  }
  getAgents(): Agent[] {
    return this.agents;
  }

  loadAgents(): void {
    this.agents = this.agentService.getAgents();
  }

  saveAgent(): void {
    if (!this.newAgent.agentName.trim()) return;

    if (this.selectedAgent) {
      this.agentService.updateAgent(this.newAgent);
    } else {
      this.agentService.addAgent(this.newAgent);
    }

    this.resetForm();
    this.loadAgents();
  }

  editAgent(agent: Agent): void {
    this.selectedAgent = { ...agent };
    this.newAgent = { ...agent };
  }

  deleteAgent(id: number): void {
    this.agentService.deleteAgent(id);
    this.loadAgents();
  }

  resetForm(): void {
    this.selectedAgent = null;
    this.newAgent = new Agent(0, '', '', '', '', '', '', '', '', 0);
  }
}
