package com.example.finalproject.service;

import com.example.finalproject.dto.CustomerDTO;
import com.example.finalproject.entity.Account;
import com.example.finalproject.entity.Customer;
import com.example.finalproject.repository.AccountRepository;
import com.example.finalproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Customer not found"));
        return convertToDto(customer);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO, MultipartFile file) throws Exception {
        Account account = accountRepository.findById(customerDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("❌ Account not found"));

        String imageFileName = saveImage(file);

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

    /**
     * ✅ **Fix: Thêm phương thức `updateCustomer`**
     */
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO, MultipartFile file) throws Exception {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Customer not found with ID: " + id));

        // Cập nhật dữ liệu nếu không null
        if (customerDTO.getBirthYear() != null) customer.setBirthYear(customerDTO.getBirthYear());
        if (customerDTO.getGender() != null) customer.setGender(customerDTO.getGender());
        if (customerDTO.getHairStyle() != null) customer.setHairStyle(customerDTO.getHairStyle());
        if (customerDTO.getPoint() != null) customer.setPoint(customerDTO.getPoint());
        if (file != null && !file.isEmpty()) customer.setImageFile(saveImage(file));

        return convertToDto(customerRepository.save(customer));
    }

    public void deleteCustomer(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("❌ Customer not found");
        }
        customerRepository.deleteById(id);
    }

    private String saveImage(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) return null;
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return fileName;
    }

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
