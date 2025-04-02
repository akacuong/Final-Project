import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { OrderDetail } from "../common/order-detail";
import { Product } from "../common/products";


@Injectable({
  providedIn: "root",
})
export class CartService {
  private cartItems: OrderDetail[] = [];
  private cartSubject = new BehaviorSubject<OrderDetail[]>(this.cartItems);
  
  cart$ = this.cartSubject.asObservable();

  constructor() {
    
    const storedCart = localStorage.getItem("cart");
    if (storedCart) {
      this.cartItems = JSON.parse(storedCart);
      this.cartSubject.next(this.cartItems);
    }
  }

  addToCart(product: Product, quantity: number) {
    const existingItem = this.cartItems.find((item) => item.product.id === product.id);
    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      this.cartItems.push(new OrderDetail(product, quantity));
    }

    this.updateCart();
  }

  removeFromCart(productId: number) {
    this.cartItems = this.cartItems.filter(item => item.product.id !== productId);
    this.updateCart();
  }
  getCartItems() {
    return this.cartItems;
  }

  clearCart() {
    this.cartItems = [];
    this.updateCart();
  }

  getTotalPrice(): number {
    return this.cartItems.reduce((total, item) => total + item.getTotalPrice(), 0);
  }

  private updateCart() {
    localStorage.setItem("cart", JSON.stringify(this.cartItems));
    this.cartSubject.next([...this.cartItems]);
  }
}
