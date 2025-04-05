import { Injectable } from '@angular/core';
import { CustomerLoyalty } from '../common/customerloyalty';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoyaltyService {
  private readonly POINT_RATE = 10000; 
  private readonly apiUrl = 'https://localhost:3306/api/loyalty';

  constructor(private http: HttpClient) {}

  // Tính điểm dựa trên tổng số tiền
  calculatePoints(totalAmount: number): number {
    return Math.floor(totalAmount / this.POINT_RATE); // Ví dụ: mỗi 10.000 VND = 1 điểm
  }

  // Lấy tổng số điểm của khách hàng từ backend
  getCustomerPoints(): number {
    let points = 0;
    this.http.get<number>(`${this.apiUrl}/points`)
      .subscribe(data => {
        points = data || 0;
      });
    return points;
  }

  // Cập nhật điểm của khách hàng trên backend
  updateCustomerPoints(newPoints: number): void {
    let currentPoints = this.getCustomerPoints();
    let updatedPoints = currentPoints + newPoints;
    this.http.post(`${this.apiUrl}/points`, { updatedPoints })
      .subscribe(response => {
        console.log('Customer points updated on backend');
      });
  }

  // Lưu lịch sử chuyển điểm vào backend
  saveLoyaltyHistory(customerLoyalty: CustomerLoyalty): void {
    this.http.post(`${this.apiUrl}/history`, customerLoyalty)
      .subscribe(response => {
        console.log('Loyalty history saved to backend');
      });
  }

  // Lấy lịch sử chuyển điểm của khách hàng từ backend
  getLoyaltyHistory(): CustomerLoyalty[] {
    let loyaltyHistory: CustomerLoyalty[] = [];
    this.http.get<CustomerLoyalty[]>(`${this.apiUrl}/history`)
      .subscribe(data => {
        loyaltyHistory = data || [];
      });
    return loyaltyHistory;
  }
}