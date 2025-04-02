import { Product } from "./products";

export class OrderDetail {
  product: Product;
  quantity: number;

  constructor(product: Product, quantity: number) {
    this.product = product;
    this.quantity = quantity;
  }

  getTotalPrice(): number {
    return this.product.price * this.quantity;
  }
}
