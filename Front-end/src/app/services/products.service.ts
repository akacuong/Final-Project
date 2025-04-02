import { Injectable } from '@angular/core';
import { Product } from '../common/products'; 

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private storageKey = 'products';

  constructor() {}

  getProducts(): Product[] {
    const data = localStorage.getItem(this.storageKey);
    return data
      ? JSON.parse(data).map((p: any) => new Product(
        Number(p.id), 
        p.name,
        String(p.image), 
        Number(p.price), 
        Number(p.category_id)
        ))
      : [];
  }
  
  
  addProduct(product: Product): void {
    const products = this.getProducts();
    const newId = products.length ? Math.max(...products.map(p => Number(p.id))) + 1 : 1;
    const newProduct = new Product(
      newId,
      product.name,
      product.image,
      Number(product.price),  
      Number(product.category_id)  
    );
  
    products.push(newProduct);
    localStorage.setItem(this.storageKey, JSON.stringify(products));
  }
  
  

  deleteProduct(id: number): void {
    const products = this.getProducts().filter(p => p.id !== id);
    localStorage.setItem(this.storageKey, JSON.stringify(products));
  }

  updateProduct(updatedProduct: Product): void {
    const products = this.getProducts().map(p => (p.id === updatedProduct.id ? updatedProduct : p));
    localStorage.setItem(this.storageKey, JSON.stringify(products));
  }
 
}
