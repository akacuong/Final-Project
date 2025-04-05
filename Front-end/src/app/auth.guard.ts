import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRoles = route.data['roles']; 
    const roles = this.authService.getRoles();

    if (!this.authService.isAuthenticated() || !roles.some(role => expectedRoles.includes(role))) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }
}