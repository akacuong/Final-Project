import { Injectable } from '@angular/core';
import { Agent } from '../common/agent';

@Injectable({
  providedIn: 'root'
})
export class AgentService {
  private storageKey = 'agents';

  constructor() {}

  getAgents(): Agent[] {
    const data = localStorage.getItem(this.storageKey);
    return data ? JSON.parse(data).map((a: any) => new Agent(
      a.id,
      a.specialization,
      a.location,
      a.establishment,
      a.openingHours,
      a.professionalSkills,
      a.ownerName,
      a.email,
  
      a.agentName,
      a.shopId, 
    )) : [];
  }

  addAgent(agent: Agent): void {
    const agents = this.getAgents();
    const newId = agents.length ? Math.max(...agents.map(a => a.id)) + 1 : 1;

    const newAgent = new Agent(
      newId,
      agent.specialization,
      agent.location,
      agent.establishment,
      agent.openingHours,
      agent.professionalSkills,
      agent.ownerName,
      agent.email, 
      agent.agentName,
      agent.shopId,
    );
    agents.push(newAgent);
    localStorage.setItem(this.storageKey, JSON.stringify(agents));
  }

  updateAgent(updatedAgent: Agent): void {
    const agents = this.getAgents().map(a => (a.id === updatedAgent.id ? updatedAgent : a));
    localStorage.setItem(this.storageKey, JSON.stringify(agents));
  }

  deleteAgent(id: number): void {
    const agents = this.getAgents().filter(a => a.id !== id);
    localStorage.setItem(this.storageKey, JSON.stringify(agents));
  }
}
