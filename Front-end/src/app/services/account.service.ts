import { Injectable } from '@angular/core';
import { Account } from '../common/account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private storageKey = 'loggedInAccount';

  // Get logged-in account from localStorage
  getLoggedInAccount(): Account | null {
    const accountData = localStorage.getItem(this.storageKey);
    return accountData ? JSON.parse(accountData) : null;
  }

  // Set account information in localStorage
  setLoggedInAccount(account: Account): void {
    localStorage.setItem(this.storageKey, JSON.stringify(account));
  }

  // Remove account from localStorage
  removeLoggedInAccount(): void {
    localStorage.removeItem(this.storageKey);
  }
}
