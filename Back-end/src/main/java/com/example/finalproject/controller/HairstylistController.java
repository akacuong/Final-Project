package com.example.finalproject.controller;

import com.example.finalproject.dto.HairstylistDTO;
import com.example.finalproject.service.HairstylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hairstylists")
public class HairstylistController {

    private final HairstylistService hairstylistService;

    public HairstylistController(HairstylistService hairstylistService) {
        this.hairstylistService = hairstylistService;
    }

    @GetMapping
    public ResponseEntity<List<HairstylistDTO>> getAllHairstylists() {
        return ResponseEntity.ok(hairstylistService.getAllHairstylists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HairstylistDTO> getHairstylistById(@PathVariable Integer id) {
        return ResponseEntity.ok(hairstylistService.getHairstylistById(id));
    }

    @PostMapping
    public ResponseEntity<HairstylistDTO> createHairstylist(
            @RequestParam("shopId") Integer shopId,
            @RequestParam("name") String name,
            @RequestParam("experience") Integer experience,
            @RequestParam("specialty") String specialty,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        HairstylistDTO hairstylistDTO = new HairstylistDTO(null, shopId, name, experience, specialty, null);
        return ResponseEntity.status(201).body(hairstylistService.createHairstylist(hairstylistDTO, image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HairstylistDTO> updateHairstylist(
            @PathVariable Integer id,
            @RequestParam("name") String name,
            @RequestParam("experience") Integer experience,
            @RequestParam("specialty") String specialty,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        HairstylistDTO hairstylistDTO = new HairstylistDTO(null, null, name, experience, specialty, null);
        return ResponseEntity.ok(hairstylistService.updateHairstylist(id, hairstylistDTO, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHairstylist(@PathVariable Integer id) {
        hairstylistService.deleteHairstylist(id);
        return ResponseEntity.ok("✅ Hairstylist đã được xóa!");
    }
}
