package com.example.finalproject.service;

import com.example.finalproject.dto.ProductDTO;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final String UPLOAD_DIR = "uploads";

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // ✅ Lấy danh sách sản phẩm
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy sản phẩm theo ID
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Product không tồn tại!"));
        return convertToDTO(product);
    }

    // ✅ Tạo sản phẩm với ảnh
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile file) throws IOException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("❌ Category không tồn tại!"));

        String imageFileName = saveImage(file);

        Product product = new Product(imageFileName, productDTO.getName(), productDTO.getPrice(), category);
        return convertToDTO(productRepository.save(product));
    }

    // ✅ Cập nhật sản phẩm (hỗ trợ cập nhật ảnh)
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO, MultipartFile file) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Product không tồn tại!"));

        if (productDTO.getName() != null) product.setName(productDTO.getName());
        if (productDTO.getPrice() != null) product.setPrice(productDTO.getPrice());

        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("❌ Category không tồn tại!"));
            product.setCategory(category);
        }

        if (file != null && !file.isEmpty()) {
            deleteImage(product.getImage());
            product.setImage(saveImage(file));
        }

        return convertToDTO(productRepository.save(product));
    }

    // ✅ Xóa sản phẩm
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Product không tồn tại!"));

        deleteImage(product.getImage());
        productRepository.deleteById(id);
    }

    // ✅ Lưu ảnh vào thư mục
    private String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        return fileName;
    }

    // ✅ Xóa ảnh cũ
    private void deleteImage(String fileName) {
        if (fileName != null) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("❌ Lỗi khi xóa ảnh: " + e.getMessage());
            }
        }
    }

    // ✅ Chuyển đổi Entity → DTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getImage(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId()
        );
    }
}
