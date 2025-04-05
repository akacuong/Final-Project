import { Injectable } from '@angular/core';
import { Shop } from '../common/shop';
import { AgentService } from './agent.service';
import { Agent } from '../common/agent';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  private apiUrl = 'https://localhost:3306/api/shop';
  private shops: Shop[] = [];

  constructor(private agentService: AgentService, private http: HttpClient) {
    this.loadShops();
  }

  private loadShops(): void {
    this.http.get<Shop[]>(this.apiUrl)
      .subscribe(data => {
        this.agentService.getAgents().subscribe(agents => {
          this.shops = data.map(s => {
            const agent = agents.find(a => a.id === s.agent?.id) || null;
            return new Shop(s.shopId, agent, s.locationShop, s.phoneNumber);
          });
        });
      });
  }

  getAllShops(): Shop[] {
    return this.shops;
  }

  getShopsByAgent(agentId: number): Shop[] {
    return this.shops.filter(shop => shop.agent?.id === agentId);
  }

  addShop(shop: Shop): void {
    this.http.post(this.apiUrl, shop)
      .subscribe(response => {
        console.log('Shop added to backend');
        this.loadShops();
      });
  }

  deleteShop(shopId: number): void {
    this.http.delete(`${this.apiUrl}/${shopId}`)
      .subscribe(response => {
        console.log('Shop deleted from backend');
        this.loadShops();
      });
  }

  getShops(): Shop[] {
    return this.shops;
  }

  getShopsByShopId(shopId: number): Shop[] {
    return this.shops.filter(shop => Number(shop.shopId) === Number(shopId));
  }
}