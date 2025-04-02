import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  
  private storageKey = 'pendingRegistration';

  saveCustomer(customer: any) {
    if (!customer) return;
    localStorage.setItem(this.storageKey, JSON.stringify(customer));
  }

  getCustomer(): any {
    const data = localStorage.getItem(this.storageKey);
    try {
      return data ? JSON.parse(data) : null;
    } catch (error) {
      console.error('Lỗi khi parse dữ liệu từ Local Storage:', error);
      return null;
    }
  }

  removeCustomer() {
    localStorage.removeItem(this.storageKey);
  }
}
