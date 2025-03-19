package com.example.finalproject.service;

import com.example.finalproject.dto.ServiceDTO;
import com.example.finalproject.entity.ServiceEntity; // ✅ Sử dụng tên mới
import com.example.finalproject.entity.Shop;
import com.example.finalproject.repository.ServiceRepository;
import com.example.finalproject.repository.ShopRepository;
import org.springframework.stereotype.Service; // ✅ Không bị trùng tên nữa

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ShopRepository shopRepository;

    public ServiceService(ServiceRepository serviceRepository, ShopRepository shopRepository) {
        this.serviceRepository = serviceRepository;
        this.shopRepository = shopRepository;
    }

    // ✅ Lấy tất cả dịch vụ
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy danh sách dịch vụ theo Shop ID
    public List<ServiceDTO> getServicesByShopId(Integer shopId) {
        return serviceRepository.findByShopId(shopId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy dịch vụ theo ID
    public ServiceDTO getServiceById(Integer id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Service không tồn tại!"));
        return convertToDTO(service);
    }

    // ✅ Tạo dịch vụ mới
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        Shop shop = shopRepository.findById(serviceDTO.getShopId())
                .orElseThrow(() -> new RuntimeException("❌ Shop không tồn tại!"));

        ServiceEntity service = new ServiceEntity(shop, serviceDTO.getName(), serviceDTO.getPrice(), serviceDTO.getStatus());
        return convertToDTO(serviceRepository.save(service));
    }

    // ✅ Cập nhật dịch vụ
    public ServiceDTO updateService(Integer id, ServiceDTO serviceDTO) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Service không tồn tại!"));

        if (serviceDTO.getName() != null) service.setName(serviceDTO.getName());
        if (serviceDTO.getPrice() != null) service.setPrice(serviceDTO.getPrice());
        if (serviceDTO.getStatus() != null) service.setStatus(serviceDTO.getStatus());

        return convertToDTO(serviceRepository.save(service));
    }

    // ✅ Xóa dịch vụ
    public void deleteService(Integer id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("❌ Service không tồn tại!");
        }
        serviceRepository.deleteById(id);
    }

    // ✅ Chuyển đổi Entity → DTO
    private ServiceDTO convertToDTO(ServiceEntity service) {
        return new ServiceDTO(
                service.getServiceId(),
                service.getShop().getId(),
                service.getName(),
                service.getPrice(),
                service.getStatus()
        );
    }
}
