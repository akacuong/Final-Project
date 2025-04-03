import { Component, OnInit } from '@angular/core';
import { Customer } from '../../../common/customer';
import { Account } from '../../../common/account';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-infor',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, FormsModule],
  templateUrl: './customer-infor.component.html',
  styleUrls: ['./customer-infor.component.css']
})
export class CustomerInforComponent implements OnInit {
  customer: Customer = new Customer(0);
  loggedInAccount: Account | null = null;
  
  defaultImagePath = 'assets/images/default-avatar.png'; 
  isEditing: boolean = false; // Trạng thái chỉnh sửa

  ngOnInit(): void {
    const storedAccount = localStorage.getItem('loggedInAccount');
    if (storedAccount) {
      this.loggedInAccount = JSON.parse(storedAccount);
    }

    const savedCustomer = localStorage.getItem('customerData');
    if (savedCustomer) {
      this.customer = JSON.parse(savedCustomer);
    }

    if (!this.customer.id || this.customer.id === 0) {
      this.customer.id = Date.now();
    }

    if (!this.customer.imageFile) {
      this.customer.imageFile = this.defaultImagePath;
    }
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.customer.imageFile = `assets/images/${file.name}`;
    }
  }

  saveCustomerInfo(): void {
    localStorage.setItem('customerData', JSON.stringify(this.customer));
    alert('Thông tin khách hàng đã được lưu!');
    this.isEditing = false; // Chuyển về trạng thái xem
  }

  editCustomerInfo(): void {
    this.isEditing = true; // Bật chế độ chỉnh sửa
  }

  navigateToCustomerInfo(): void {
    window.location.href = '/customer/customer-infor'; // Chuyển hướng đến trang thông tin khách hàng
  }
}
