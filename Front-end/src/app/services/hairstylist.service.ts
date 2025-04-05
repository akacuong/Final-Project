import { Injectable } from '@angular/core';
import { HairStylist } from '../common/hairstylist';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HairStylistService {
  private hairstylists: HairStylist[] = [];
  private apiUrl = 'https://3306/hairstylists';

  constructor(private http: HttpClient) {
    this.loadFromBackend();  
  }
  
  private saveToBackend(): void {
    this.http.post(this.apiUrl, this.hairstylists)
        .subscribe(response => {
            console.log('Data saved to backend');
        });
  }

  private loadFromBackend(): void {
    this.http.get<HairStylist[]>(this.apiUrl)
        .subscribe(data => {
            this.hairstylists = data || [];
        });
  }

  getStylistsByShopId(shopId: number): HairStylist[] {
    return this.hairstylists.filter(stylist => Number(stylist.shopId) === Number(shopId));
  }
  
  addStylist(stylist: HairStylist): void {
    stylist.stylist_id = this.hairstylists.length + 1;
    this.hairstylists.push(stylist);
    this.saveToBackend();
  }

  updateStylist(updatedStylist: HairStylist): void {
    const index = this.hairstylists.findIndex(s => s.stylist_id === updatedStylist.stylist_id);
    if (index !== -1) {
      this.hairstylists[index] = { ...updatedStylist };
      this.saveToBackend();
    }
  }

  removeStylist(stylistId: number): void {
    this.hairstylists = this.hairstylists.filter(stylist => stylist.stylist_id !== stylistId);
    this.saveToBackend();
  }

  getBookedTimes(stylistId: number, date: Date): string[] {
    return ['10:00', '14:30', '16:00']; 
  }
}