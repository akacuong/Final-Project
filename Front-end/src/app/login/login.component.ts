import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { AccountService } from '../services/account.service';

import { Account } from '../common/account';
import { JwtHelperService } from '../services/jwthelper.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private router: Router) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;

      this.authService.login(username, password).subscribe({
        next: (response) => {
          const token = response.token;
          const role = response.role;
          this.authService.saveToken(token);
          console.log('Token:', this.authService.getToken());
          if (role === 'ADMIN') {
            this.router.navigate(['/manager/dashboard-manager']);
            alert('Đăng nhập thành công!');
          } 
          alert('Đăng nhập thành công!');
        },
        error: (err) => {
          this.errorMessage = 'Đăng nhập thất bại. Vui lòng thử lại!';
          console.error('Error status:', err.status);
          console.error('Error message:', err.message);
          console.error('Error details:', err);
          alert('Đăng nhập thất bại: ' + (err.error || 'Không xác định'));
        }
      });
    } else {
      alert('Vui lòng nhập đầy đủ thông tin!');
    }
  }
}