import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Account } from '../common/account'; // Import Account model
import { AccountService } from '../services/account.service'; // Import AccountService
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private accountService: AccountService // Inject AccountService
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
      phoneNumber: ['', [Validators.required]]
    });
  }

  ngOnInit() {}

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.errorMessage = 'Vui lòng nhập đầy đủ thông tin hợp lệ!';
      return;
    }

    const { username, email, password, confirmPassword, phoneNumber } = this.registerForm.value;

    if (password !== confirmPassword) {
      this.errorMessage = 'Mật khẩu và xác nhận mật khẩu không khớp!';
      return;
    }

    // Create a new account object
    const account = new Account(
      Date.now(), // accountId using current timestamp
      username,
      email,
      password,
      phoneNumber
    );

    // Store the account directly in localStorage
    this.accountService.setLoggedInAccount(account);

    alert('Đăng ký thành công! Dữ liệu đã được lưu trên trình duyệt.');
    this.router.navigate(['/login']);
  }
}
