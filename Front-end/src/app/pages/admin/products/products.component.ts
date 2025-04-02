import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Product } from '../../../common/products'; 
import { ProductsService } from '../../../services/products.service';

@Component({
  selector: 'app-manage-products',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule], 
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductComponent implements OnInit {
  categories = this.getCategories(); 
  private productList: Product[] = [];
  filteredProducts: Product[] = [];
  selectedProduct: Product | null = null;
  newProduct: Product = new Product(0, '', '', 0, 1);
  showAddForm = false;

  constructor(private productService: ProductsService) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productList = this.productService.getProducts();
    this.filteredProducts = [...this.productList];
  }

  filterByCategory(categoryId: number) {
    this.filteredProducts = this.productList.filter(product => product.category_id === categoryId);
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id);
    this.loadProducts();
  }

  editProduct(product: Product) {
    this.selectedProduct = { ...product }; 
  }

  saveEdit() {
    if (this.selectedProduct) {
      this.productService.updateProduct(this.selectedProduct);
      this.selectedProduct = null;
      this.loadProducts();
    }
  }

  toggleAddProduct() {
    this.showAddForm = !this.showAddForm;
  }

  addProduct() {
    if (this.newProduct.name.trim()) {
      this.productService.addProduct(this.newProduct);
      this.newProduct = new Product(0, '', '', 0, 1);
      this.toggleAddProduct();
      this.loadProducts();
    }
  }

  getCategories() {
    const storageKey = 'categories';
    let categories = localStorage.getItem(storageKey);
    
    if (!categories) {
      const defaultCategories = [
        { id: 1, name: 'Electronics' },
        { id: 2, name: 'Clothing' },
        { id: 3, name: 'Home & Kitchen' },
        { id: 4, name: 'Beauty' }
      ];
      localStorage.setItem(storageKey, JSON.stringify(defaultCategories));
      return defaultCategories;
    }
    return JSON.parse(categories);
  }
}
