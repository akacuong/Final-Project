package com.example.finalproject.controller;

import com.example.finalproject.dto.ProductDTO;
import com.example.finalproject.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ GET all products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ✅ GET product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // ✅ POST - Create product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ProductDTO dto = new ProductDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setCategoryId(categoryId);

        return ResponseEntity.status(201).body(productService.createProduct(dto, file));
    }

    // ✅ PUT - Update product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Integer id,
            @RequestParam("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ProductDTO dto = new ProductDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setCategoryId(categoryId);

        return ResponseEntity.ok(productService.updateProduct(id, dto, file));
    }

    // ✅ DELETE - Xoá sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
