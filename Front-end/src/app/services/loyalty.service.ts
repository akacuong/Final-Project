import { Injectable } from '@angular/core';
import { CustomerLoyalty } from '../common/customerloyalty';

@Injectable({
  providedIn: 'root'
})
export class LoyaltyService {

  private readonly POINT_RATE = 10000; 
  private readonly STORAGE_KEY = 'loyaltyPoints';
  private readonly LOYALTY_HISTORY_KEY = 'loyaltyHistory'; // Lưu lịch sử chuyển điểm

  constructor() {}

  // Tính điểm dựa trên tổng số tiền
  calculatePoints(totalAmount: number): number {
    return Math.floor(totalAmount / this.POINT_RATE); // Ví dụ: mỗi 10.000 VND = 1 điểm
  }

  // Lấy tổng số điểm của khách hàng từ localStorage
  getCustomerPoints(): number {
    const points = localStorage.getItem(this.STORAGE_KEY);
    return points ? parseInt(points, 10) : 0;
  }

  // Cập nhật điểm của khách hàng trong localStorage
  updateCustomerPoints(newPoints: number): void {
    let currentPoints = this.getCustomerPoints();
    let updatedPoints = currentPoints + newPoints;
    localStorage.setItem(this.STORAGE_KEY, updatedPoints.toString());
  }

  // Lưu lịch sử chuyển điểm vào localStorage
  saveLoyaltyHistory(customerLoyalty: CustomerLoyalty): void {
    let loyaltyHistory: CustomerLoyalty[] = JSON.parse(localStorage.getItem(this.LOYALTY_HISTORY_KEY) || "[]");
    loyaltyHistory.push(customerLoyalty);
    localStorage.setItem(this.LOYALTY_HISTORY_KEY, JSON.stringify(loyaltyHistory));
  }

  // Lấy lịch sử chuyển điểm của khách hàng từ localStorage
  getLoyaltyHistory(): CustomerLoyalty[] {
    return JSON.parse(localStorage.getItem(this.LOYALTY_HISTORY_KEY) || "[]");
  }
}
