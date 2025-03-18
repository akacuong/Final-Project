package com.example.finalproject.controller;

import com.example.finalproject.dto.CustomerDTO;
import com.example.finalproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(
            @RequestParam("birthYear") String birthYear,
            @RequestParam("gender") String gender,
            @RequestParam("hairStyle") String hairStyle,
            @RequestParam("point") Integer point,
            @RequestParam("accountId") Integer accountId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setBirthYear(LocalDate.parse(birthYear));
        customerDTO.setGender(gender);
        customerDTO.setHairStyle(hairStyle);
        customerDTO.setPoint(point);
        customerDTO.setAccountId(accountId);

        return ResponseEntity.status(201).body(customerService.createCustomer(customerDTO, imageFile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Integer id,
            @RequestParam("birthYear") String birthYear,
            @RequestParam("gender") String gender,
            @RequestParam("hairStyle") String hairStyle,
            @RequestParam("point") Integer point,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setBirthYear(LocalDate.parse(birthYear));
        customerDTO.setGender(gender);
        customerDTO.setHairStyle(hairStyle);
        customerDTO.setPoint(point);

        return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO, imageFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
