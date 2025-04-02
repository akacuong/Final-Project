import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Account } from '../common/account';
import { Customer } from '../common/customer';
import { CommonModule } from '@angular/common';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-register',
  standalone: true, 
  imports: [CommonModule, ReactiveFormsModule, RouterModule], 
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
      phoneNumber: ['', [Validators.required]],
      birthYear: ['', [Validators.required]],
      gender: ['', [Validators.required]],
      hairStyle: [''],
      imageFile: ['']
    });
  }

  ngOnInit() {
    const savedCustomer = this.customerService.getCustomer();
    if (savedCustomer) {
      console.log('Đã tải dữ liệu từ Local Storage:', savedCustomer);
      this.customerService.removeCustomer();
    }
  }
  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.errorMessage = 'Vui lòng nhập đầy đủ thông tin hợp lệ!';
      return;
    }
  
    const { username, email, password, confirmPassword, phoneNumber, birthYear, gender, hairStyle, imageFile } = this.registerForm.value;
  
    // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp không
    if (password !== confirmPassword) {
      this.errorMessage = 'Mật khẩu và xác nhận mật khẩu không khớp!';
      return;
    }
  
    // Chuyển birthYear thành Date hợp lệ
    const parsedBirthYear = new Date(birthYear);
    if (isNaN(parsedBirthYear.getTime())) {
      this.errorMessage = 'Năm sinh không hợp lệ!';
      return;
    }
  
    // Tạo tài khoản
    const account = new Account(Date.now(), username, email, password, phoneNumber);
  
    // Tạo khách hàng với ID ngẫu nhiên
    const customer = new Customer(Date.now(), parsedBirthYear, gender, hairStyle, 0, imageFile, account);
  
    // Lưu vào Local Storage
    this.customerService.saveCustomer(customer);
    console.log('Đã lưu vào Local Storage:', customer);
  
    alert('Đăng ký thành công! Dữ liệu đã được lưu trên trình duyệt.');
    this.router.navigate(['/login']);
  }
  
}
