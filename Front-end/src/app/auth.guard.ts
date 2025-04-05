import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from './services/auth.service';
import { JwtHelperService } from './services/jwthelper.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router,
    private jwtHelper: JwtHelperService
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const token = this.authService.getToken();
    console.log('✅ AuthGuard is running');
    console.log('Token:', token);

    if (!token || this.jwtHelper.isTokenExpired(token)) {
      alert('Bạn chưa đăng nhập!');
      console.log('❌ Token không hợp lệ hoặc đã hết hạn');
      this.router.navigate(['/login']);
      return false;
    }

    
    const requiredRoles = route.data['roles'];
    const decodedToken = this.jwtHelper.decodeToken(token);
    const userRole = decodedToken?.role;

    console.log('✅ Decoded token:', decodedToken);
    console.log('🔐 User role:', userRole);
    console.log('🔒 Required roles:', requiredRoles);

    if (!requiredRoles || !userRole || !requiredRoles.includes(userRole)) {
      alert('Bạn không có quyền truy cập!');
      console.log('❌ Không có quyền truy cập');
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}
