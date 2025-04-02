import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CartService } from '../../../services/cart.service';

// Định nghĩa CartItem interface
interface CartItem {
  product: {
    name: string;
    price: number;
    image?: string;
    description?: string;
  };
  quantity: number;
}

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];   
  
  constructor
  (
    private cartService: CartService,
    private router: Router

  ) {}

  ngOnInit(): void {
    this.cartService.cart$.subscribe((items: CartItem[]) => {
      this.cartItems = items;
    });
  }

  get totalCartPrice(): number {
    return this.cartItems.reduce((total, item) => total + item.product.price * item.quantity, 0);
  }

  increaseQuantity(item: CartItem) {
    item.quantity++;
  }

  decreaseQuantity(item: CartItem) {
    if (item.quantity > 1) {
      item.quantity--;
    }
  }

  getTotal(): number {
    return this.cartItems.reduce((total, item) => total + item.product.price * item.quantity, 0);
  }
  goToPayment() {
    this.router.navigate(["customer/payment"]);
  }
}
