import { Component, OnInit } from '@angular/core';
import { HairStylist } from '../../../common/hairstylist';
import { HairStylistService } from '../../../services/hairstylist.service';
import { ShopService } from '../../../services/shop.service';
import { Shop } from '../../../common/shop';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-hairstylist',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './hairstylist.component.html',
  styleUrls: ['./hairstylist.component.css']
})
export class HairStylistComponent implements OnInit {
  hairstylists: HairStylist[] = [];
  newStylist: HairStylist = new HairStylist(0, 0, '', 0, '', 'Active', '');
  selectedShopId: number = 0;
  shops: Shop[] = [];  
  editingStylist: HairStylist | null = null;
  selectedShopLocation: string = '';

  constructor(
    private hairstylistService: HairStylistService,
    private shopService: ShopService
  ) {}

  ngOnInit(): void {
    this.loadShops();
  }

  loadShops(): void {
    this.shops = this.shopService.getShops();  

    if (this.shops.length > 0) {
      const savedShopId = Number(localStorage.getItem('selectedShopId'));
      this.selectedShopId = savedShopId || this.shops[0].shopId;

      const shop = this.shops.find(s => s.shopId === this.selectedShopId);
      this.selectedShopLocation = shop ? shop.locationShop : 'Unknown Shop';

      this.loadStylists();
    } else {
      console.warn("No shops found!");
    }
  }

  updateShopLocation(): void {
    const shop = this.shops.find(s => s.shopId === this.selectedShopId);
    this.selectedShopLocation = shop ? shop.locationShop : 'Unknown Location';
  }

  loadStylists(): void {
    this.hairstylists = this.hairstylistService.getStylistsByShopId(Number(this.selectedShopId));
  }
  
  changeShop(shopId: number): void {
    this.selectedShopId = Number(shopId);
    localStorage.setItem('selectedShopId', this.selectedShopId.toString());
    this.updateShopLocation();
    this.loadStylists();
  }
  

  addStylist(): void {
    if (!this.newStylist.name.trim()) return;
    this.newStylist.shopId = this.selectedShopId;
    this.hairstylistService.addStylist(this.newStylist);
    this.newStylist = new HairStylist(0, this.selectedShopId, '', 0, '', 'Active', '');
    this.loadStylists();
  }

  removeStylist(id: number): void {
    if (window.confirm("Do you want to delete?")) {
      this.hairstylistService.removeStylist(id);
      this.loadStylists();
    }
  }

  editStylist(stylist: HairStylist): void {
    this.editingStylist = { ...stylist };
  }

  updateStylist(): void {
    if (this.editingStylist) {
      this.hairstylistService.updateStylist(this.editingStylist);
      this.editingStylist = null;
      this.loadStylists();
    }
  }
}
