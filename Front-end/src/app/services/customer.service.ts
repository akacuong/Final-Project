import { Injectable } from '@angular/core';
import { Customer } from '../common/customer';
import { Account } from '../common/account';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private apiUrl = 'http://localhost:8080/api/customer';

  constructor(private http: HttpClient) {}

  saveCustomer(customer: Customer) {
    if (!customer.id || customer.id === 0) {
      customer.id = Date.now(); 
    }
    this.http.post(`${this.apiUrl}/save`, customer)
      .subscribe(response => {
        console.log('Customer data saved to backend');
      });
  }

  getCustomer(): Customer | null {
    let customer: Customer | null = null;
    this.http.get<Customer>(`${this.apiUrl}/get`)
      .subscribe(data => {
        customer = data || null;
      });
    return customer;
  }

  getCustomerData(): Customer | null {
    let customerData: Customer | null = null;
    this.http.get<Customer>(`${this.apiUrl}/data`)
      .subscribe(data => {
        customerData = data || null;
      });
    return customerData;
  }

  removeCustomer() {
    this.http.delete(`${this.apiUrl}/remove`)
      .subscribe(response => {
        console.log('Customer data removed from backend');
      });
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