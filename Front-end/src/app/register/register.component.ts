import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Account } from '../common/account';
import { AccountService } from '../services/account.service';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import * as bcrypt from 'bcryptjs';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  errorMessage: string = '';

  constructor(private fb: FormBuilder,
              private accountService: AccountService,
              private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],  // Thêm điều khiển `confirmPassword`
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],  // Thêm điều khiển `phoneNumber`
      role: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const { username, password, confirmPassword, email, phoneNumber, role } = this.registerForm.value;

      if (password !== confirmPassword) {
        this.errorMessage = 'Passwords do not match.';
        return;
      }

      this.accountService.register(username, password, email, phoneNumber, role).subscribe({
        next: (response) => {
          const token = response.token;
          this.authService.saveToken(token);
          console.log('Token:', this.authService.getToken());
          if (role === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/customer']);
          }
          alert('Đăng ký thành công!');
        },
        error: (err) => {
          this.errorMessage = 'Đăng ký thất bại. Vui lòng thử lại!';
          console.error('Error status:', err.status);
          console.error('Error message:', err.message);
          console.error('Error details:', err);
          alert('Đăng ký thất bại: ' + (err.error || 'Không xác định'));
        }
      });
    } else {
      alert('Vui lòng nhập đầy đủ thông tin!');
    }
  }
}