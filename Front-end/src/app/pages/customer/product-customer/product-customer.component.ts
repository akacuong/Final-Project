import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProductsService } from '../../../services/products.service';
import { CategoryService } from '../../../services/category.service';
import { Product } from '../../../common/products';
import { Category } from '../../../common/category';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CartService } from '../../../services/cart.service';

@Component({
  selector: 'app-product-customer',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-customer.component.html',
  styleUrls: ['./product-customer.component.css']
})

export class ProductCustomerComponent implements OnInit {
  productList: Product[] = [];
  categories: Category[] = [];
  filteredProducts: Product[] = [];
  selectedCategory: number | null = null;
  productId: string | null = null;
  product: Product | null = null;
  quantity: number = 1;

  constructor(private productService: ProductsService, private categoryService: CategoryService, private route: ActivatedRoute,private cartService: CartService,private router: Router,) {}

  ngOnInit() {
    this.loadProducts();
    this.loadCategories();
    this.route.paramMap.subscribe(params => {
      const categoryId = params.get('categoryId');
      if (categoryId) {
        this.selectedCategory = +categoryId; // Chuyển đổi sang số
        this.filterByCategory(this.selectedCategory);
      }
    });
  }

  loadProducts() {
    this.productList = this.productService.getProducts();
    this.filteredProducts = [...this.productList];
  }

  loadCategories() {
    this.categories = this.categoryService.getCategories();
  }

  filterByCategory(categoryId: number | null) {
    this.selectedCategory = categoryId;
    this.filteredProducts = categoryId
      ? this.productList.filter(product => product.category_id === categoryId)
      : [...this.productList];
  }
  addToCart(product: Product) {
    if (product) {
      this.cartService.addToCart(product, this.quantity);
      console.log(`Added ${this.quantity} of ${product.name} to cart`);
      
      // Navigate to the cart page
      this.router.navigate(['customer/cart']);
    } else {
      console.log("Product not found!");
    }
  }
  
  
}
