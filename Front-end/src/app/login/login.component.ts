import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { AccountService } from '../services/account.service';
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
              private router: Router,
              private jwtHelper: JwtHelperService) { }

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
          if (typeof response === 'string' && response.startsWith('❌')) {
            this.errorMessage = response;
            alert(response); // Hiển thị thông báo từ backend
            return;
          }
          // Lưu token vào localStorage
          this.authService.saveToken(token);
  
          // Giải mã token để lấy thông tin role
          const decodedToken = this.jwtHelper.decodeToken(token);
          const role = decodedToken?.role;
  
          console.log('Response from login:', response);  // Kiểm tra xem có trả về đúng role không
          console.log('Decoded Role:', role);  // Kiểm tra giá trị của role sau khi giải mã token
  
          console.log('Token:', this.authService.getToken());
  
          if (role === 'ADMIN') {
            this.router.navigate(['manager/dashboard-manager']);
            alert('Đăng nhập thành công với quyền ADMIN!');
          } else if (role === 'AGENT') {
            this.router.navigate(['admin/dashboard']);
            alert('Đăng nhập thành công với quyền AGENT!');
          } else {
            this.router.navigate(['customer/booking']);
            alert('Đăng nhập thành công với quyền CUSTOMER!');
          }
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
