import { Injectable } from '@angular/core';
import { HairStylist } from '../common/hairstylist';

@Injectable({
  providedIn: 'root'
})
export class HairStylistService {
  private hairstylists: HairStylist[] = [];

  constructor() {
    this.loadFromLocalStorage();  
  }
  private saveToLocalStorage(): void {
    localStorage.setItem('hairstylists', JSON.stringify(this.hairstylists));
  }

  private loadFromLocalStorage(): void {
    const data = localStorage.getItem('hairstylists');
    this.hairstylists = data ? JSON.parse(data) : [];
  }

  getStylistsByShopId(shopId: number): HairStylist[] {
    return this.hairstylists.filter(stylist => Number(stylist.shopId) === Number(shopId));
  }
  
  
  addStylist(stylist: HairStylist): void {
    stylist.stylist_id = this.hairstylists.length + 1;
    this.hairstylists.push(stylist);
    this.saveToLocalStorage();
  }

  updateStylist(updatedStylist: HairStylist): void {
    const index = this.hairstylists.findIndex(s => s.stylist_id === updatedStylist.stylist_id);
    if (index !== -1) {
      this.hairstylists[index] = { ...updatedStylist };
      this.saveToLocalStorage();
    }
  }

  removeStylist(stylistId: number): void {
    this.hairstylists = this.hairstylists.filter(stylist => stylist.stylist_id !== stylistId);
    this.saveToLocalStorage();
  }
  getBookedTimes(stylistId: number, date: Date): string[] {
    return ['10:00', '14:30', '16:00']; 
  }
}
