import { Injectable } from '@angular/core';
import { Customer } from '../common/customer';
import { Account } from '../common/account';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private storageKey = 'customerData';

  saveCustomer(customer: Customer) {
    if (!customer.id || customer.id === 0) {
      customer.id = Date.now(); 
    }
    localStorage.setItem(this.storageKey, JSON.stringify(customer));
  }

  getCustomer(): Customer | null {
    const data = localStorage.getItem(this.storageKey);
    try {
      return data ? JSON.parse(data) : null;
    } catch (error) {
      console.error('Lỗi khi parse dữ liệu từ Local Storage:', error);
      return null;
    }
  }
  getCustomerData(): Customer | null {
    const customerData = localStorage.getItem('customerData');
    return customerData ? JSON.parse(customerData) : null;
  }
  removeCustomer() {
    localStorage.removeItem(this.storageKey);
  }
  initializeCustomerData(account: Account): Customer {
    // Initialize customer data with default values or based on the account
    const customer = new Customer();
    customer.account = account;
    customer.id = Date.now(); // Use current timestamp for unique ID
    customer.point = 0; // Initialize with 0 points
    customer.imageFile = 'assets/images/default-avatar.png'; // Default image
    return customer;
  }
}
