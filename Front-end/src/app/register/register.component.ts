import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Account } from '../common/account';
import { AccountService } from '../services/account.service';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

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
  private apiUrl = 'https://localhost:8080/api/accounts';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private accountService: AccountService,
    private http: HttpClient
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
      phoneNumber: ['', [Validators.required]],
      role: ['', [Validators.required]]
    });
  }

  ngOnInit() {}

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.errorMessage = 'Vui lòng nhập đầy đủ thông tin hợp lệ!';
      return;
    }

    const { username, email, password, confirmPassword, phoneNumber, role } = this.registerForm.value;

    if (password !== confirmPassword) {
      this.errorMessage = 'Mật khẩu và xác nhận mật khẩu không khớp!';
      return;
    }

    const status = role === 'AGENT' ? false : true;

    const account = new Account(
      Date.now(),
      username,
      email,
      password,
      phoneNumber,
      new Date(),
      status,
      role
    );

    this.storeAccount(account);
  }

  private storeAccount(account: Account): void {
    this.http.post(this.apiUrl, account)
      .subscribe(response => {
        console.log('Account data saved to backend');
        alert('Đăng ký thành công! Dữ liệu đã được lưu trên trình duyệt.');
        this.router.navigate(['/login']);
      });
  }
}