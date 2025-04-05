import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],  
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const username = this.loginForm.value.username.trim();
      const password = this.loginForm.value.password.trim();
  
      this.authService.login(username, password).subscribe(
        (token: string) => {
          this.authService.saveToken(token);
          const roles = this.authService.getRoles();
          if (roles.includes('ADMIN')) {
            this.router.navigate(['/manager/dashboard-manager']);
          } else {
            alert('Access Denied');
          }
        },
        (error) => {
          console.error(error); // Debug lỗi rõ hơn
          alert('Đăng nhập thất bại: ' + (error.error || 'Không xác định'));
        }
      );
    }
  }
  
  
}
