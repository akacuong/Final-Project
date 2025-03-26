package com.example.finalproject.service;

import com.example.finalproject.dto.ProductDTO;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Product;
import com.example.finalproject.repository.CategoryRepository;
import com.example.finalproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImgurService imgurService; // ✅ Thêm ImgurService

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ImgurService imgurService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imgurService = imgurService;
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

    // ✅ Tạo sản phẩm mới và upload ảnh
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile file) throws IOException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("❌ Category không tồn tại!"));

        String imageUrl = (file != null && !file.isEmpty()) ? imgurService.uploadToImgur(file) : null;

        Product product = new Product(imageUrl, productDTO.getName(), productDTO.getPrice(), category);
        return convertToDTO(productRepository.save(product));
    }

    // ✅ Cập nhật sản phẩm (có hỗ trợ ảnh mới)
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
            product.setImage(imgurService.uploadToImgur(file)); // ✅ Cập nhật ảnh mới
        }

        return convertToDTO(productRepository.save(product));
    }

    // ✅ Xóa sản phẩm
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Product không tồn tại!"));

        // ❌ Không cần xóa ảnh vì ảnh lưu trên Imgur (anonymous) không quản lý được
        productRepository.deleteById(id);
    }

    // ✅ Convert entity → DTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getImage(), // URL ảnh từ Imgur
                product.getName(),
                product.getPrice(),
                product.getCategory().getId()
        );
    }
}
