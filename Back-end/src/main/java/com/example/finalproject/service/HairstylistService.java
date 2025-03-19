package com.example.finalproject.service;

import com.example.finalproject.dto.HairstylistDTO;
import com.example.finalproject.entity.Hairstylist;
import com.example.finalproject.entity.Shop;
import com.example.finalproject.repository.HairstylistRepository;
import com.example.finalproject.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HairstylistService {

    private final HairstylistRepository hairstylistRepository;
    private final ShopRepository shopRepository;
    private static final String UPLOAD_DIR = "uploads/";

    public HairstylistService(HairstylistRepository hairstylistRepository, ShopRepository shopRepository) {
        this.hairstylistRepository = hairstylistRepository;
        this.shopRepository = shopRepository;
    }

    // ✅ Lấy danh sách hairstylists
    public List<HairstylistDTO> getAllHairstylists() {
        return hairstylistRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy hairstylist theo ID
    public HairstylistDTO getHairstylistById(Integer id) {
        Hairstylist hairstylist = hairstylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Hairstylist không tồn tại!"));
        return convertToDTO(hairstylist);
    }

    // ✅ Tạo hairstylist mới với ảnh
    public HairstylistDTO createHairstylist(HairstylistDTO hairstylistDTO, MultipartFile file) throws IOException {
        Shop shop = shopRepository.findById(hairstylistDTO.getShopId())
                .orElseThrow(() -> new RuntimeException("❌ Shop không tồn tại!"));

        // ✅ Lưu ảnh nếu có
        String imageFileName = (file != null && !file.isEmpty()) ? saveImage(file) : null;

        // ✅ Tạo hairstylist mới
        Hairstylist hairstylist = new Hairstylist();
        hairstylist.setShop(shop);
        hairstylist.setName(hairstylistDTO.getName());
        hairstylist.setExperience(hairstylistDTO.getExperience());
        hairstylist.setSpecialty(hairstylistDTO.getSpecialty());
        hairstylist.setImage(imageFileName);

        return convertToDTO(hairstylistRepository.save(hairstylist));
    }

    // ✅ Cập nhật hairstylist (có hỗ trợ cập nhật ảnh)
    public HairstylistDTO updateHairstylist(Integer id, HairstylistDTO hairstylistDTO, MultipartFile file) throws IOException {
        Hairstylist hairstylist = hairstylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Hairstylist không tồn tại!"));

        // ✅ Cập nhật thông tin nếu không null
        if (hairstylistDTO.getShopId() != null) {
            Shop shop = shopRepository.findById(hairstylistDTO.getShopId())
                    .orElseThrow(() -> new RuntimeException("❌ Shop không tồn tại!"));
            hairstylist.setShop(shop);
        }

        if (hairstylistDTO.getName() != null) hairstylist.setName(hairstylistDTO.getName());
        if (hairstylistDTO.getExperience() != null) hairstylist.setExperience(hairstylistDTO.getExperience());
        if (hairstylistDTO.getSpecialty() != null) hairstylist.setSpecialty(hairstylistDTO.getSpecialty());

        // ✅ Kiểm tra nếu có file ảnh mới
        if (file != null && !file.isEmpty()) {
            deleteImage(hairstylist.getImage()); // Xóa ảnh cũ
            hairstylist.setImage(saveImage(file)); // Lưu ảnh mới
        }

        return convertToDTO(hairstylistRepository.save(hairstylist));
    }

    // ✅ Xóa hairstylist
    public void deleteHairstylist(Integer id) {
        Hairstylist hairstylist = hairstylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Hairstylist không tồn tại!"));

        deleteImage(hairstylist.getImage()); // Xóa ảnh trước khi xóa hairstylist
        hairstylistRepository.deleteById(id);
    }

    // ✅ Lưu ảnh vào thư mục (Chỉ tạo thư mục nếu chưa có)
    private String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // ✅ Kiểm tra xem thư mục đã tồn tại chưa, nếu chưa thì tạo
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // ✅ Tạo tên file duy nhất
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // ✅ Lưu file vào thư mục
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
    private HairstylistDTO convertToDTO(Hairstylist hairstylist) {
        return new HairstylistDTO(
                hairstylist.getId(),
                hairstylist.getShop().getId(),
                hairstylist.getName(),
                hairstylist.getExperience(),
                hairstylist.getSpecialty(),
                hairstylist.getImage()
        );
    }
}
