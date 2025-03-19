package com.example.finalproject.controller;

import com.example.finalproject.dto.ServiceDTO;
import com.example.finalproject.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // ✅ Lấy tất cả dịch vụ
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    // ✅ Lấy danh sách dịch vụ theo Shop ID
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<ServiceDTO>> getServicesByShopId(@PathVariable Integer shopId) {
        return ResponseEntity.ok(serviceService.getServicesByShopId(shopId));
    }

    // ✅ Lấy dịch vụ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Integer id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO) {
        return ResponseEntity.status(201).body(serviceService.createService(serviceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable Integer id, @RequestBody ServiceDTO serviceDTO) {
        return ResponseEntity.ok(serviceService.updateService(id, serviceDTO));
    }

    // ✅ Xóa dịch vụ
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Integer id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok("✅ Dịch vụ đã được xóa!");
    }
}
