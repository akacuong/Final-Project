import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductsService } from '../../../services/products.service';
import { CartService } from '../../../services/cart.service';
import { Product } from '../../../common/products';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  productId: string | null = null;
  product: Product | null = null;
  quantity: number = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,  // Thêm Router vào constructor
    private productService: ProductsService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('id');
    if (this.productId) {
      const productIdNumber = Number(this.productId);
      this.product = this.productService.getProducts().find(p => p.id === productIdNumber) || null;
    }
  }

  increaseQuantity() {
    this.quantity++;
  }

  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart() {
    if (this.product) {
      this.cartService.addToCart(this.product, this.quantity);
      console.log(`Added ${this.quantity} of ${this.product.name} to cart`);

      // Chuyển sang trang giỏ hàng
      this.router.navigate(['customer/cart']);
    } else {
      console.log("Product not found!");
    }
  }

  get totalPrice(): number {
    return this.product ? this.product.price * this.quantity : 0;
  }
}
