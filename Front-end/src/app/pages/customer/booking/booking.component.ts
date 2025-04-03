import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Shop } from '../../../common/shop';
import { HairStylist } from '../../../common/hairstylist';
import { ShopService } from '../../../services/shop.service';
import { HairStylistService } from '../../../services/hairstylist.service';
import { Service } from '../../../common/service';
import { ServiceService } from '../../../services/service.service';
import { Booking } from '../../../common/booking';
import { ActivatedRoute, Router } from '@angular/router';
import { LoyaltyService } from '../../../services/loyalty.service';
import { CustomerLoyalty } from '../../../common/customerloyalty';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class BookingComponent implements OnInit {
  selectedDate: Date | null = null;
  formattedDate: string = '';
  shops: Shop[] = [];
  selectedShopId: number | null = null;
  hairStylists: HairStylist[] = [];
  selectedStylist: number | null = null;
  services: Service[] = [];
  selectedShopLocation: string = '';
  selectedServices: Service[] = [];
  availableTimes: string[] = [];
  bookedTimes: string[] = [];
  selectedTime: string | null = null;

  constructor(
    private shopService: ShopService,
    private hairStylistService: HairStylistService,
    private serviceService: ServiceService,
    private route: ActivatedRoute,
    private loyaltyService: LoyaltyService,
  ) {}

  ngOnInit(): void {
    this.loadShops();
    this.loadServices();
     
    this.route.queryParams.subscribe(params => {
      console.log("Query Params:", params); // Kiểm tra dữ liệu truyền vào
  
      if (params['selectedServices']) {
        const parsedServices: Service[] = JSON.parse(params['selectedServices']);
        console.log("Parsed Services:", parsedServices); // Kiểm tra danh sách dịch vụ
  
        parsedServices.forEach(service => {
          if (!this.selectedServices.some(s => s.id === service.id)) {
            this.selectedServices.push({ ...service, checked: true });
          }
        });
  
        console.log("Updated Selected Services:", this.selectedServices);
      }
    });
    
    
  }

  loadShops(): void {
    this.shops = this.shopService.getShops();
  }

  onShopChange(): void {
    if (this.selectedShopId !== null) {
      const selectedShop = this.shops.find(shop => shop.shopId === this.selectedShopId);
      this.selectedShopLocation = selectedShop ? selectedShop.locationShop : '';
      this.hairStylists = this.hairStylistService.getStylistsByShopId(this.selectedShopId);
    } else {
      this.hairStylists = [];
    }
    this.selectedStylist = null;
    this.availableTimes = [];
    this.selectedTime = null;
  }

  onDateChange(event: Date): void {
    this.selectedDate = event;
    this.formatDateDisplay();
    this.loadAvailableTimes();
  }

  formatDateDisplay(): void {
    if (!this.selectedDate) return;
    const options: Intl.DateTimeFormatOptions = { 
      weekday: 'long', 
      day: '2-digit', 
      month: '2-digit', 
      year: 'numeric',
    };
    this.formattedDate = new Intl.DateTimeFormat('vi-VN', options).format(this.selectedDate);
  }

  loadServices(): void {
    this.services = this.serviceService.getServices();
  }

  calculateTotal(): number {
    return this.selectedServices.reduce((total, service) => total + service.price, 0);
  }

  onServiceChange(service: Service, event: Event): void {
    const isChecked = (event.target as HTMLInputElement).checked;
    if (isChecked) {
      if (!this.selectedServices.some(s => s.id === service.id)) {
        this.selectedServices.push(service);
      }
    } else {
      this.selectedServices = this.selectedServices.filter(s => s.id !== service.id);
    }
  }

  onStylistChange(): void {
    if (this.selectedStylist !== null && this.selectedDate) {
      this.loadAvailableTimes();
    }
  }

  loadAvailableTimes(): void {
    if (!this.selectedStylist || !this.selectedDate || !this.selectedShopId) return;

    let bookings: Booking[] = JSON.parse(localStorage.getItem("bookings") || "[]");

    this.bookedTimes = bookings
      .filter(booking =>
        booking.stylist_id === this.selectedStylist &&
        booking.shopId === this.selectedShopId &&
        this.isSameDate(booking.datetime, this.selectedDate!)
      )
      .map(booking => booking.datetime.split(" ")[0].substring(0, 5));

    const allTimes = [
      '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', 
      '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00'
    ];

    this.availableTimes = allTimes.filter(time => !this.bookedTimes.includes(time));
  }

  isSameDate(storedDateTime: string, selectedDate: Date): boolean {
    const [day, month, year] = storedDateTime.split(" ")[1].split("/").map(num => parseInt(num, 10));
    return (
      selectedDate.getDate() === day &&
      selectedDate.getMonth() + 1 === month &&
      selectedDate.getFullYear() === year
    );
  }

  isTimeBooked(time: string): boolean {
    return this.bookedTimes.includes(time);
  }

  onSelectTime(time: string): void {
    this.selectedTime = time;
  }

  bookAppointment(): void {
    if (!this.selectedStylist || !this.selectedDate || this.selectedServices.length === 0 || !this.selectedTime) {
      alert("Vui lòng điền đầy đủ thông tin trước khi đặt lịch!");
      return;
    }
  
    const storedAccount = localStorage.getItem('loggedInAccount');
    const storedCustomer = localStorage.getItem('customerData');
  
    if (!storedAccount || !storedCustomer) {
      alert("Không tìm thấy thông tin tài khoản! Vui lòng đăng nhập lại.");
      return;
    }
  
    const account = JSON.parse(storedAccount);
    const customer = JSON.parse(storedCustomer);
  
    // Tính toán điểm tích lũy
    const totalAmount = this.calculateTotal();
    const loyaltyPoints = this.loyaltyService.calculatePoints(totalAmount);
  
    // Cập nhật điểm mới vào localStorage
    this.loyaltyService.updateCustomerPoints(loyaltyPoints);
  
    // Lưu thông tin lịch sử chuyển điểm
    const newCustomerLoyalty = new CustomerLoyalty(
      new Date().getTime(),
      customer.name,
      loyaltyPoints,
      new Date(),
      customer.id,
      account.id
    );
    this.loyaltyService.saveLoyaltyHistory(newCustomerLoyalty);
  
    // Lưu thông tin đặt lịch vào localStorage
    const bookingDate = new Date(this.selectedDate);
    const [hours, minutes] = this.selectedTime.split(':').map(num => parseInt(num, 10));
    bookingDate.setHours(hours, minutes, 0, 0);
  
    const bookingDateTimeVN = bookingDate.toLocaleString('vi-VN', { timeZone: 'Asia/Ho_Chi_Minh' });
  
    const newBooking: Booking = {
      booking_id: new Date().getTime(),
      shopId: this.selectedShopId!,
      stylist_id: this.selectedStylist!,
      datetime: bookingDateTimeVN,
      status: "Confirmed",
      payment_status: "Pending",
      total_price: totalAmount,
      customer_id: customer.id, 
      customer_phone: account.phoneNumber, 
    };
  
    let bookings: Booking[] = JSON.parse(localStorage.getItem("bookings") || "[]");
    bookings.push(newBooking);
    localStorage.setItem("bookings", JSON.stringify(bookings));
  
    alert(`Đặt lịch thành công! Bạn đã tích lũy ${loyaltyPoints} điểm!`);
  
    // Reset thông tin sau khi đặt lịch
    this.selectedStylist = null;
    this.selectedDate = null;
    this.selectedTime = null;
    this.selectedServices = [];
    this.formattedDate = '';
  }
  
  
  
  

  isServiceChecked(service: Service): boolean {
    return this.selectedServices.some(s => s.id === service.id);
  }
  
  onServiceCheckedChange(service: Service, checked: boolean) {
    if (checked) {
      this.selectedServices.push(service);
    } else {
      this.selectedServices = this.selectedServices.filter(s => s.id !== service.id);
    }
  }
  
  
}
