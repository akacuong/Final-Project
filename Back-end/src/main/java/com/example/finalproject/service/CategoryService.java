package com.example.finalproject.service;

import com.example.finalproject.dto.CategoryDTO;
import com.example.finalproject.entity.Category;
import com.example.finalproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ✅ Lấy tất cả categories
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy category theo ID
    public CategoryDTO getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Category không tồn tại!"));
        return convertToDTO(category);
    }

    // ✅ Tạo category mới
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new RuntimeException("❌ Category đã tồn tại!");
        }

        Category category = new Category(categoryDTO.getName());
        return convertToDTO(categoryRepository.save(category));
    }

    // ✅ Cập nhật category
    public CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Category không tồn tại!"));

        if (categoryDTO.getName() != null) category.setName(categoryDTO.getName());

        return convertToDTO(categoryRepository.save(category));
    }

    // ✅ Xóa category
    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("❌ Category không tồn tại!");
        }
        categoryRepository.deleteById(id);
    }

    // ✅ Chuyển đổi Entity -> DTO
    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
