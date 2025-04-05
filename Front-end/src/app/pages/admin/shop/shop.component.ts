import { Component, OnInit } from '@angular/core';
import { Shop } from '../../../common/shop';
import { ShopService } from '../../../services/shop.service';
import { AgentService } from '../../../services/agent.service';
import { Agent } from '../../../common/agent';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-shop',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {
  agents: Agent[] = [];
  shopsByAgent: { [agentId: number]: Shop[] } = {};
  newShop: Shop = new Shop(0, null, '', '');

  constructor(private shopService: ShopService, private agentService: AgentService) {}

  ngOnInit(): void {
    this.loadAgentsAndShops();
  }

  loadAgentsAndShops(): void {
    this.agentService.getAgents().subscribe(agents => {
      this.agents = agents;
      this.agents.forEach(agent => {
        this.shopsByAgent[agent.id] = this.shopService.getShopsByAgent(agent.id);
      });
    });
  }

  saveShop(agent: Agent): void {
    if (!this.newShop.locationShop.trim() || !this.newShop.phoneNumber.trim()) return;
    this.newShop.agent = agent;
    this.shopService.addShop(this.newShop);
    this.newShop = new Shop(0, null, '', '');
    this.loadAgentsAndShops();
  }

  deleteShop(shopId: number): void {
    this.shopService.deleteShop(shopId);
    this.loadAgentsAndShops();
  }
}