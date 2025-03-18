package com.example.finalproject.dto;

import com.example.finalproject.entity.Account.Role;
import java.time.LocalDateTime;

public class AccountDTO {
    private Integer accountId;
    private String username;
    private String phoneNumber;
    private LocalDateTime submittedAt;
    private Boolean status;
    private Role role;

    public AccountDTO() {}

    public AccountDTO(Integer accountId, String username, String phoneNumber, LocalDateTime submittedAt, Boolean status, Role role) {
        this.accountId = accountId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.submittedAt = submittedAt;
        this.status = status;
        this.role = role;
    }

    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { this.accountId = accountId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
