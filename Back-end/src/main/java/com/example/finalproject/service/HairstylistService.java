package com.example.finalproject.service;

import com.example.finalproject.dto.HairstylistDTO;
import com.example.finalproject.entity.Hairstylist;
import com.example.finalproject.entity.Shop;
import com.example.finalproject.repository.HairstylistRepository;
import com.example.finalproject.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HairstylistService {

    private final HairstylistRepository hairstylistRepository;
    private final ShopRepository shopRepository;
    private final ImgurService imgurService; // ✅ Thêm ImgurService

    public HairstylistService(
            HairstylistRepository hairstylistRepository,
            ShopRepository shopRepository,
            ImgurService imgurService
    ) {
        this.hairstylistRepository = hairstylistRepository;
        this.shopRepository = shopRepository;
        this.imgurService = imgurService;
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

        String imageUrl = (file != null && !file.isEmpty()) ? imgurService.uploadToImgur(file) : null;

        Hairstylist hairstylist = new Hairstylist();
        hairstylist.setShop(shop);
        hairstylist.setName(hairstylistDTO.getName());
        hairstylist.setExperience(hairstylistDTO.getExperience());
        hairstylist.setSpecialty(hairstylistDTO.getSpecialty());
        hairstylist.setImage(imageUrl);

        return convertToDTO(hairstylistRepository.save(hairstylist));
    }

    // ✅ Cập nhật hairstylist (có hỗ trợ cập nhật ảnh)
    public HairstylistDTO updateHairstylist(Integer id, HairstylistDTO hairstylistDTO, MultipartFile file) throws IOException {
        Hairstylist hairstylist = hairstylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Hairstylist không tồn tại!"));

        if (hairstylistDTO.getShopId() != null) {
            Shop shop = shopRepository.findById(hairstylistDTO.getShopId())
                    .orElseThrow(() -> new RuntimeException("❌ Shop không tồn tại!"));
            hairstylist.setShop(shop);
        }

        if (hairstylistDTO.getName() != null) hairstylist.setName(hairstylistDTO.getName());
        if (hairstylistDTO.getExperience() != null) hairstylist.setExperience(hairstylistDTO.getExperience());
        if (hairstylistDTO.getSpecialty() != null) hairstylist.setSpecialty(hairstylistDTO.getSpecialty());

        if (file != null && !file.isEmpty()) {
            // ❌ Không cần xoá ảnh cũ (Imgur anonymous không có quyền xoá)
            hairstylist.setImage(imgurService.uploadToImgur(file));
        }

        return convertToDTO(hairstylistRepository.save(hairstylist));
    }

    // ✅ Xoá hairstylist
    public void deleteHairstylist(Integer id) {
        Hairstylist hairstylist = hairstylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Hairstylist không tồn tại!"));

        // ❌ Không cần xoá ảnh (ảnh ẩn danh trên Imgur không thể quản lý)
        hairstylistRepository.deleteById(id);
    }

    // ✅ Convert Entity → DTO
    private HairstylistDTO convertToDTO(Hairstylist hairstylist) {
        return new HairstylistDTO(
                hairstylist.getId(),
                hairstylist.getShop().getId(),
                hairstylist.getName(),
                hairstylist.getExperience(),
                hairstylist.getSpecialty(),
                hairstylist.getImage() // là URL Imgur
        );
    }
}
