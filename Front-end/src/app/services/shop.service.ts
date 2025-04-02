import { Injectable } from '@angular/core';
import { Shop } from '../common/shop';
import { AgentService } from './agent.service';
import { Agent } from '../common/agent';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  private storageKey = 'shops';
  private shops: Shop[] = [];

  constructor(private agentService: AgentService) {
    this.loadShops();
  }

  private loadShops(): void {
    const data = localStorage.getItem(this.storageKey);
    this.shops = data ? JSON.parse(data).map((s: any) => {
      const agent = this.agentService.getAgents().find(a => a.id === s.agent?.id) || null;
      return new Shop(s.shopId, agent, s.locationShop, s.phoneNumber);
    }) : [];
  }

  getAllShops(): Shop[] {
    return this.shops;
  }

  getShopsByAgent(agentId: number): Shop[] {
    return this.shops.filter(shop => shop.agent?.id === agentId);
  }

  addShop(shop: Shop): void {
    const newId = this.shops.length ? Math.max(...this.shops.map(s => s.shopId)) + 1 : 1;
    const newShop = new Shop(newId, shop.agent, shop.locationShop, shop.phoneNumber);
    this.shops.push(newShop);
    this.saveShops();
  }

  deleteShop(shopId: number): void {
    this.shops = this.shops.filter(shop => shop.shopId !== shopId);
    this.saveShops();
  }

  private saveShops(): void {
    localStorage.setItem(this.storageKey, JSON.stringify(this.shops));
  }
  getShops(): Shop[] {
    return this.shops;
  }
  getShopsByShopId(shopId: number): Shop[] {
    return this.shops.filter(shop => Number(shop.shopId) === Number(shopId));
  }
  
}
