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

  getAgents(): void {
    this.agentService.getAgents().subscribe((data) => {
      this.agents = data;
    });
  }

  loadAgents(): void {
    this.agentService.getAgents().subscribe(
      (data: Agent[]) => {
        this.agents = data;  
        console.log('Agents loaded successfully', data);
      },
      error => {
        console.error('Error loading agents', error);
      }
    );
  }

  saveAgent(): void {
    if (!this.newAgent.agentName.trim()) return;
  
    if (this.selectedAgent) {
      this.agentService.updateAgent(this.newAgent).subscribe(() => {
        this.loadAgents();
      });
    } else {
      this.agentService.addAgent(this.newAgent).subscribe(() => {
        this.loadAgents();
      });
    }
  
    this.resetForm();
  }

  editAgent(agent: Agent): void {
    this.newAgent = { ...agent }; 
    this.selectedAgent = { ...agent };  
  }

  deleteAgent(id: number): void {
    this.agentService.deleteAgent(id).subscribe(() => {
      this.loadAgents();
      alert('Agent has been deleted successfully!');
    }, error => {
      console.error('There was an error!', error);
      alert('Failed to delete the agent!');
    });
  }

  resetForm(): void {
    this.selectedAgent = null;
    this.newAgent = new Agent(0, '', '', '', '', '', '', '', '', 0);
    alert('Form has been reset!');
  }
}
