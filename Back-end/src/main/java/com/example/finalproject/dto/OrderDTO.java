package com.example.finalproject.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {
    private Integer id;
    private Integer customerId;
    private String status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

    public OrderDTO() {}

    public OrderDTO(Integer id, Integer customerId, String status, BigDecimal totalPrice, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public Integer getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(String status) { this.status = status; }
}
