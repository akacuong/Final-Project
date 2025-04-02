import { Injectable } from '@angular/core';
import { Category } from '../common/category';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private storageKey = 'categories';

  constructor() {}

  getCategories(): Category[] {
    const data = localStorage.getItem(this.storageKey);
    return data ? JSON.parse(data).map((c: any) => new Category(c.id, c.name)) : [];
  }

  addCategory(category: Category): void {
    const categories = this.getCategories();

    
    const newId = categories.length > 0 ? Math.max(...categories.map(c => c.category_id)) + 1 : 1;
    const newCategory = new Category(newId, category.name);

    categories.push(newCategory);
    localStorage.setItem(this.storageKey, JSON.stringify(categories));
  }

  deleteCategory(id: number): void {
    const categories = this.getCategories().filter(category => category.category_id !== id);
    localStorage.setItem(this.storageKey, JSON.stringify(categories));
  }
}
