package com.example.finalproject.entity;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public enum Role {
        CUSTOMER, AGENT, ADMIN
    }

    public Account() {}

    // ✅ Constructor chỉ gọi setPassword() để tránh mã hóa 2 lần
    public Account(String username, String email, String password, String phoneNumber, Role role) {
        this.username = username;
        this.email = email;
        this.setPassword(password); // ✅ Gọi setPassword()
        this.phoneNumber = phoneNumber;
        this.submittedAt = LocalDateTime.now(); // ✅ Gán thời gian tạo mặc định
        this.status = true; // ✅ Mặc định tài khoản đang hoạt động
        this.role = role;
    }

    // ✅ Tránh mã hóa mật khẩu 2 lần
    public void setPassword(String password) {
        if (!password.startsWith("$2a$")) { // ✅ Chỉ mã hóa nếu chưa mã hóa
            this.password = new BCryptPasswordEncoder().encode(password);
        } else {
            this.password = password;
        }
    }

    // ✅ Getters & Setters
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // ✅ Kiểm tra tài khoản có đang hoạt động không
    public boolean isActive() {
        return Boolean.TRUE.equals(this.status);
    }
}
