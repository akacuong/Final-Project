package com.example.finalproject.controller;

import com.example.finalproject.dto.LoginRequest;
import com.example.finalproject.dto.RegisterRequest;
import com.example.finalproject.service.AuthService; // ✅ Thiếu import, thêm vào
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // ✅ Đảm bảo AuthService được inject bằng Constructor
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        String response = authService.register(request);
        if (response.startsWith("❌")) {
            return ResponseEntity.badRequest().body(response); // ✅ Return 400 if registration fails
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String response = authService.login(request); // ✅ Gọi từ `authService`
        if (response.startsWith("❌")) {
            return ResponseEntity.status(403).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
