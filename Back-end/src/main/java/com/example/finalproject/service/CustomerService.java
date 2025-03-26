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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ImgurService imgurService; // ✅ Gọi ImgurService đã tạo

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

    // ✅ Tạo khách hàng mới và upload ảnh lên Imgur
    public CustomerDTO createCustomer(CustomerDTO customerDTO, MultipartFile file) throws IOException {
        Account account = accountRepository.findById(customerDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("❌ Account not found"));

        String imageUrl = (file != null && !file.isEmpty()) ? imgurService.uploadToImgur(file) : null;

        Customer customer = new Customer(
                customerDTO.getBirthYear(),
                customerDTO.getGender(),
                customerDTO.getHairStyle(),
                customerDTO.getPoint() != null ? customerDTO.getPoint() : 0,
                imageUrl,
                account
        );

        return convertToDto(customerRepository.save(customer));
    }

    // ✅ Cập nhật khách hàng (upload ảnh mới nếu có)
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO, MultipartFile file) throws IOException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Customer not found with ID: " + id));

        if (customerDTO.getBirthYear() != null) customer.setBirthYear(customerDTO.getBirthYear());
        if (customerDTO.getGender() != null) customer.setGender(customerDTO.getGender());
        if (customerDTO.getHairStyle() != null) customer.setHairStyle(customerDTO.getHairStyle());
        if (customerDTO.getPoint() != null) customer.setPoint(customerDTO.getPoint());

        if (file != null && !file.isEmpty()) {
            customer.setImageFile(imgurService.uploadToImgur(file));
        }

        return convertToDto(customerRepository.save(customer));
    }

    // ✅ Xóa khách hàng
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Customer not found"));
        customerRepository.deleteById(id);
    }

    // ✅ Chuyển đổi Entity → DTO
    private CustomerDTO convertToDto(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getBirthYear(),
                customer.getGender(),
                customer.getHairStyle(),
                customer.getPoint(),
                customer.getImageFile(), // imageFile là URL từ Imgur
                customer.getAccount().getAccountId()
        );
    }
}
