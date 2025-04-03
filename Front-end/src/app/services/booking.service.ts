import { Injectable } from '@angular/core';
import { Booking } from '../common/booking';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private bookings: Booking[] = [];

  constructor() {
    this.loadBookingsFromStorage();
  }

  getBookings(): Booking[] {
    return this.bookings;
  }

  addBooking(booking: Booking): void {
    this.bookings.push(booking);
    this.saveBookingsToStorage();
  }

  setBookingData(bookingData: Booking): void {
    localStorage.setItem('currentBooking', JSON.stringify(bookingData));
  }

  getBookingData(): Booking | null {  
    const storedBooking = localStorage.getItem('currentBooking');
    return storedBooking ? JSON.parse(storedBooking) : null;
  }

  clearBookingData(): void {
    localStorage.removeItem('currentBooking');
  }

  private saveBookingsToStorage(): void {
    localStorage.setItem('bookings', JSON.stringify(this.bookings));
  }

  private loadBookingsFromStorage(): void {
    const storedBookings = localStorage.getItem('bookings');
    if (storedBookings) {
      this.bookings = JSON.parse(storedBookings);
    }
  }
}
