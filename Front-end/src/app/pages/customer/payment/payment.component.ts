import { Component, OnInit } from "@angular/core";
import { CartService } from "../../../services/cart.service";
import { OrderService } from "../../../services/order.service";
import { PaymentService } from "../../../services/payment.service";
import { Order } from "../../../common/order";
import { Payment } from "../../../common/payment";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { Customer } from "../../../common/customer";
import { Account } from "../../../common/account";

@Component({
  selector: "app-payment",
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: "./payment.component.html",
  styleUrls: ["./payment.component.css"],
})
export class PaymentComponent implements OnInit {
  totalPrice = 0;
  cartItems: any[] = [];
  selectedPaymentMethod: string = "COD"; 

  currentCustomer: Customer = new Customer(1, new Date('1990-01-01'), 'Male', 'Short', 500, 'image.jpg', new Account(1, 'john_doe', 'john.doe@example.com', 'password123', '123-456-7890', new Date(), true, 'CUSTOMER'));

  constructor(
    private cartService: CartService, 
    private orderService: OrderService,
    private paymentService: PaymentService
  ) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();  
    this.totalPrice = this.cartService.getTotalPrice();
  }

  confirmPayment() {
    if (this.cartItems.length === 0) {
      alert("Your cart is empty!");
      return;
    }

    const customerId = this.currentCustomer.id ?? 0; // Default to 0 if undefined
    const accountId = this.currentCustomer.account?.accountId;

    const order = new Order(customerId);
    order.totalPrice = this.totalPrice;
    order.status = "pending";
    order.paymentStatus = this.selectedPaymentMethod;
    order.orderId = this.generateOrderId(); // Assign orderId dynamically

    const payment = new Payment(
      this.generatePaymentId(),
      order.orderId!,
      this.generateBookingId(),
      this.selectedPaymentMethod,
      new Date().toISOString()
    );

    localStorage.setItem('paymentDetails', JSON.stringify({
      customerId: order.customerId,
      accountId: accountId,
      totalPrice: order.totalPrice,
      status: order.status,
      paymentStatus: order.paymentStatus,
      cartItems: this.cartItems,
      customerName: this.currentCustomer.account?.username,
      phoneNumber: this.currentCustomer.account?.phoneNumber
    }));

    this.orderService.placeOrder(order); 
    this.paymentService.processPayment(payment); // Process payment
    this.cartService.clearCart(); // Clear the cart
    alert("Payment successful!");

    window.location.href = ""; // Redirect to a confirmation page
  }

  private generateOrderId(): number {
    return Math.floor(Math.random() * 1000000);
  }

  private generatePaymentId(): number {
    return Math.floor(Math.random() * 1000000);
  }

  private generateBookingId(): number {
    return Math.floor(Math.random() * 1000000);
  }
}
