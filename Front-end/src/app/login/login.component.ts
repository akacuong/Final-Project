import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AccountService } from '../services/account.service'; 
import { LoyaltyService } from '../services/loyalty.service'; 
import { CustomerService } from '../services/customer.service'; 

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
    private accountService: AccountService, 
    private loyaltyService: LoyaltyService, 
    private customerService: CustomerService 
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const email = this.loginForm.value.email.trim();
      const password = this.loginForm.value.password.trim();

      const account = this.accountService.getLoggedInAccount(); 

      if (account && account.email && account.password) {
        const storedEmail = account.email.trim();
        const storedPassword = account.password.trim();
        
        if (email === storedEmail && password === storedPassword) {
          alert('Đăng nhập thành công!');
          localStorage.setItem('loggedInUser', storedEmail);  
          localStorage.setItem('loggedInAccount', JSON.stringify(account));

          
          let points = this.loyaltyService.getCustomerPoints();
          if (points === 0) {
            this.loyaltyService.updateCustomerPoints(0); 
          }

          
          let customerData = this.customerService.getCustomerData();
          if (!customerData) {
          
            const newCustomerData = this.customerService.initializeCustomerData(account);
            localStorage.setItem('customerData', JSON.stringify(newCustomerData));
          }

          this.router.navigate(['']);
        } else {
          alert('Email hoặc mật khẩu không chính xác');
        }
      } else {
        alert('Thông tin tài khoản không hợp lệ');
      }
    }
  }
}
