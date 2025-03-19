package com.example.finalproject.controller;

import com.example.finalproject.dto.ShopDTO;
import com.example.finalproject.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        return ResponseEntity.ok(shopService.getAllShops());
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<ShopDTO>> getShopsByAgentId(@PathVariable Integer agentId) {
        return ResponseEntity.ok(shopService.getShopsByAgentId(agentId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopById(@PathVariable Integer id) {
        Optional<ShopDTO> shop = shopService.getShopById(id);

        if (shop.isPresent()) {
            return ResponseEntity.ok(shop.get());
        } else {
            return ResponseEntity.status(404).body("❌ Shop không tồn tại!");
        }
    }
    @PostMapping
    public ResponseEntity<ShopDTO> createShop(@RequestBody ShopDTO shopDTO) {
        return ResponseEntity.status(201).body(shopService.createShop(shopDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopDTO> updateShop(@PathVariable Integer id, @RequestBody ShopDTO shopDTO) {
        return ResponseEntity.ok(shopService.updateShop(id, shopDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Integer id) {
        shopService.deleteShop(id);
        return ResponseEntity.ok("✅ Shop đã được xóa!");
    }
}
