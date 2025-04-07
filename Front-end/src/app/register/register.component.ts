import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AccountService } from '../services/account.service';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';

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
      confirmPassword: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
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
        next: (response: any) => {
            if (response.token) {
                this.authService.saveToken(response.token);
                const decodedToken = this.authService.decodeToken(response.token);
                const userRole = decodedToken?.role;

                if (userRole === 'CUSTOMER') {
                  this.router.navigate(['/customer/booking']);
                  alert('Đăng ký thành công! Đã đăng nhập với quyền CUSTOMER.');
                } else if (userRole === 'AGENT') {
                  this.router.navigate(['/admin/dashboard']); // Chuyển hướng AGENT vào trang dashboard
                  alert('Đăng ký thành công! Đã đăng nhập với quyền AGENT.');
                }
            }
        },
        error: (err) => {
            this.errorMessage = 'Đăng ký thất bại. Vui lòng thử lại!';
            console.error('Error during registration: ', err);
            alert('Đăng ký thất bại: ' + (err.error || 'Không xác định'));
        }
      });
    }
  }
}