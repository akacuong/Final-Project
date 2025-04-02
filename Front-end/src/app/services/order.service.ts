import { Injectable } from "@angular/core";
import { Order } from "../common/order";

@Injectable({
  providedIn: "root",
})
export class OrderService {
  private storageKey = "orders"; 

  constructor() {}

  
  placeOrder(order: Order): void {
    const orders = this.getOrders();
    orders.push(order);
    localStorage.setItem(this.storageKey, JSON.stringify(orders));
  }


  getOrders(): Order[] {
    const storedOrders = localStorage.getItem(this.storageKey);
    return storedOrders ? JSON.parse(storedOrders) : [];
  }


  clearOrders(): void {
    localStorage.removeItem(this.storageKey);
  }
}
