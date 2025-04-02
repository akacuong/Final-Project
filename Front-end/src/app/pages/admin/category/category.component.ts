import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../../services/category.service';
import { Category } from '../../../common/category';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {
  
  categories: Category[] = [];
  newCategoryName: string = '';

  constructor(private categoryService: CategoryService) {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categories = this.categoryService.getCategories();
  }

  addCategory(): void {
    if (this.newCategoryName.trim()) {
      const newCategory = new Category(0, this.newCategoryName);
      this.categoryService.addCategory(newCategory);
      this.newCategoryName = '';
      this.loadCategories();
    }
  }

  deleteCategory(id: number): void {
    this.categoryService.deleteCategory(id);
    this.loadCategories();
  }
}