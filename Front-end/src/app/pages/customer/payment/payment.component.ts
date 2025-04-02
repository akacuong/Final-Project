import { Component, OnInit } from "@angular/core";
import { CartService } from "../../../services/cart.service";
import { OrderService } from "../../../services/order.service";
import { Order } from "../../../common/order";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";

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

  constructor(private cartService: CartService, private orderService: OrderService) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();  
    this.totalPrice = this.cartService.getTotalPrice();
  }

  confirmPayment() {
    if (this.cartItems.length === 0) {
      alert("Your cart is empty!");
      return;
    }

    const customerId = 1;
    const order = new Order(customerId);
    order.totalPrice = this.totalPrice;
    order.status = "pending";
    order.paymentStatus = this.selectedPaymentMethod;

    this.orderService.placeOrder(order);
    this.cartService.clearCart();
    alert("Payment successful!");

    window.location.href = "";
  }
}
