import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoyaltyService {
  
  private readonly POINT_RATE = 10000; 
  private readonly STORAGE_KEY = 'loyaltyPoints';

  constructor() {}

 
  calculatePoints(totalAmount: number): number {
    return Math.floor(totalAmount / this.POINT_RATE);
  }

 
  getCustomerPoints(): number {
    const points = localStorage.getItem(this.STORAGE_KEY);
    return points ? parseInt(points, 10) : 0;
  }

  
  updateCustomerPoints(newPoints: number): void {
    let currentPoints = this.getCustomerPoints();
    let updatedPoints = currentPoints + newPoints;
    localStorage.setItem(this.STORAGE_KEY, updatedPoints.toString());
  }
}
