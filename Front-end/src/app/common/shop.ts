import { Agent } from './agent';

export class Shop {
  constructor(
    public shopId: number, 
    public agent: Agent | null,
    public locationShop: string, 
    public phoneNumber: string 
  ) {}
}
