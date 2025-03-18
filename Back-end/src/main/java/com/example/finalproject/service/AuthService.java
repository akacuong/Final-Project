package com.example.finalproject.service;

import com.example.finalproject.dto.LoginRequest;
import com.example.finalproject.dto.RegisterRequest;
import com.example.finalproject.entity.Account;
import com.example.finalproject.entity.Account.Role;
import com.example.finalproject.repository.AccountRepository;
import com.example.finalproject.config.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    // ✅ Đăng ký tài khoản mới
    public String register(RegisterRequest request) {
        if (accountRepository.findByUsername(request.getUsername()).isPresent()) {
            return "❌ Username already exists!";
        }

        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            return "❌ Invalid role! Must be CUSTOMER, AGENT, or ADMIN.";
        }

        // ✅ Mã hóa mật khẩu trước khi lưu
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        System.out.println("🔹 Hashed password before saving: " + hashedPassword); // Debug mật khẩu

        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        account.setPassword(hashedPassword); // ✅ Chỉ mã hóa 1 lần
        account.setPhoneNumber(request.getPhoneNumber());
        account.setRole(role);
        account.setStatus(true);
        account.setSubmittedAt(LocalDateTime.now());

        accountRepository.save(account);
        return "✅ User registered successfully!";
    }

    // ✅ Đăng nhập tài khoản
    public String login(LoginRequest request) {
        Optional<Account> userOptional = accountRepository.findByUsername(request.getUsername());

        if (userOptional.isEmpty()) {
            System.out.println("❌ User not found: " + request.getUsername());
            return "❌ User not found!";
        }

        Account account = userOptional.get();

        // ✅ Debug: In ra mật khẩu đã mã hóa trong database
        System.out.println("🔹 Hashed password in DB: " + account.getPassword());
        System.out.println("🔹 Raw password input: " + request.getPassword());

        // ✅ So sánh mật khẩu đã nhập với mật khẩu trong database
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            System.out.println("❌ Password does not match!");
            return "❌ Invalid credentials!";
        }

        System.out.println("✅ Login successful for user: " + account.getUsername());

        // ✅ Tạo JWT Token sau khi đăng nhập thành công
        return jwtUtils.generateToken(account.getUsername(), account.getRole().name());
    }
}
