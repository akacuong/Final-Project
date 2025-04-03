import { Injectable } from '@angular/core';
import { Payment } from '../common/payment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor() { }
  processPayment(payment: Payment) {
 
    console.log('Processing payment:', payment);


  }
}
