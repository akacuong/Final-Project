package com.example.finalproject.service;

import com.example.finalproject.dto.CustomerDTO;
import com.example.finalproject.entity.Account;
import com.example.finalproject.entity.Customer;
import com.example.finalproject.repository.AccountRepository;
import com.example.finalproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    private static final String UPLOAD_DIR = "uploads/";

    // ✅ Constructor để đảm bảo thư mục được tạo khi khởi động ứng dụng
    public CustomerService() {
        createUploadDirectory();
    }

    // ✅ Lấy danh sách tất cả khách hàng
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ✅ Lấy khách hàng theo ID
    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Customer not found"));
        return convertToDto(customer);
    }

    // ✅ Tạo khách hàng mới và lưu ảnh
    public CustomerDTO createCustomer(CustomerDTO customerDTO, MultipartFile file) throws IOException {
        Account account = accountRepository.findById(customerDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("❌ Account not found"));

        String imageFileName = (file != null && !file.isEmpty()) ? saveImage(file) : null;

        Customer customer = new Customer(
                customerDTO.getBirthYear(),
                customerDTO.getGender(),
                customerDTO.getHairStyle(),
                customerDTO.getPoint() != null ? customerDTO.getPoint() : 0,
                imageFileName,
                account
        );

        return convertToDto(customerRepository.save(customer));
    }

    // ✅ Cập nhật khách hàng (bao gồm ảnh)
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO, MultipartFile file) throws IOException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Customer not found with ID: " + id));

        // Cập nhật thông tin nếu có
        if (customerDTO.getBirthYear() != null) customer.setBirthYear(customerDTO.getBirthYear());
        if (customerDTO.getGender() != null) customer.setGender(customerDTO.getGender());
        if (customerDTO.getHairStyle() != null) customer.setHairStyle(customerDTO.getHairStyle());
        if (customerDTO.getPoint() != null) customer.setPoint(customerDTO.getPoint());

        // ✅ Nếu có ảnh mới, xóa ảnh cũ trước khi lưu ảnh mới
        if (file != null && !file.isEmpty()) {
            deleteImage(customer.getImageFile());  // Xóa ảnh cũ
            customer.setImageFile(saveImage(file)); // Lưu ảnh mới
        }

        return convertToDto(customerRepository.save(customer));
    }

    // ✅ Xóa khách hàng (xóa luôn ảnh nếu có)
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Customer not found"));

        deleteImage(customer.getImageFile()); // Xóa ảnh trước khi xóa khách hàng
        customerRepository.deleteById(id);
    }

    // ✅ Lưu ảnh vào thư mục
    private String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // ✅ Tạo thư mục nếu chưa tồn tại
        createUploadDirectory();

        // ✅ Tạo tên file duy nhất
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        // ✅ Lưu file vào thư mục
        Files.write(filePath, file.getBytes());

        return fileName;
    }

    // ✅ Xóa ảnh cũ khi cập nhật hoặc xóa khách hàng
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

    // ✅ Tạo thư mục `uploads/` nếu chưa tồn tại
    private void createUploadDirectory() {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                System.err.println("❌ Lỗi khi tạo thư mục uploads: " + e.getMessage());
            }
        }
    }

    // ✅ Chuyển đổi Entity → DTO
    private CustomerDTO convertToDto(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getBirthYear(),
                customer.getGender(),
                customer.getHairStyle(),
                customer.getPoint(),
                customer.getImageFile(),
                customer.getAccount().getAccountId()
        );
    }
}
