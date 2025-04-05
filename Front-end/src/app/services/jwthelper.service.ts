import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtHelperService {

  constructor() { }

  // ✅ Giải mã token
  decodeToken(token: string): any {
    if (!token) return null;
    try {
      const payload = token.split('.')[1];
      return JSON.parse(atob(payload));
    } catch (error) {
      console.error('Lỗi giải mã token:', error);
      return null;
    }
  }

  // ✅ Kiểm tra token đã hết hạn chưa
  isTokenExpired(token: string): boolean {
    const decoded = this.decodeToken(token);
    if (!decoded || !decoded.exp) return true;
    const expiryTime = decoded.exp * 1000; // vì exp là giây, Date.now() là mili giây
    return Date.now() > expiryTime;
  }

  // ✅ Lấy thông tin người dùng từ token
  getUserFromToken(token: string): any {
    const decoded = this.decodeToken(token);
    if (decoded) {
      return {
        username: decoded.sub || '',
        role: decoded.role || ''
      };
    }
    return null;
  }
}
