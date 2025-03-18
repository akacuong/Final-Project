package com.example.finalproject.controller;

import com.example.finalproject.dto.StyleDTO;
import com.example.finalproject.service.StyleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/styles")
public class StyleController {

    private final StyleService styleService;

    public StyleController(StyleService styleService) {
        this.styleService = styleService;
    }

    // ✅ Get all Styles
    @GetMapping
    public ResponseEntity<List<StyleDTO>> getAllStyles() {
        return ResponseEntity.ok(styleService.getAllStyles());
    }

    // ✅ Get Style by ID
    @GetMapping("/{id}")
    public ResponseEntity<StyleDTO> getStyleById(@PathVariable Long id) {
        Optional<StyleDTO> style = styleService.getStyleById(id);
        return style.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // 🔥 Fix: Use `notFound().build()` instead of `badRequest().body("String")`
    }

    // ✅ Create Style
    @PostMapping
    public ResponseEntity<StyleDTO> createStyle(@RequestBody StyleDTO styleDTO) {
        Optional<StyleDTO> createdStyle = styleService.createStyle(styleDTO);
        return createdStyle
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build()); // 🔥 Fix: `badRequest().build()`
    }

    // ✅ Update Style
    @PutMapping("/{id}")
    public ResponseEntity<StyleDTO> updateStyle(@PathVariable Long id, @RequestBody StyleDTO styleDTO) {
        Optional<StyleDTO> updatedStyle = styleService.updateStyle(id, styleDTO);
        return updatedStyle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // 🔥 Fix: Use `notFound().build()`
    }

    // ✅ Delete Style
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStyle(@PathVariable Long id) {
        boolean isDeleted = styleService.deleteStyle(id);
        return isDeleted
                ? ResponseEntity.ok("✅ Style đã được xóa!")
                : ResponseEntity.notFound().build(); // 🔥 Fix: Use `notFound().build()` instead of `badRequest().body("String")`
    }
}
