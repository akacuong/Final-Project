package com.example.finalproject.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingDTO {
    private Integer bookingId;
    private Integer customerId;
    private Integer shopId;
    private LocalDateTime datetime;
    private String status;
    private String paymentStatus;
    private BigDecimal totalPrice;

    public BookingDTO() {}

    public BookingDTO(Integer bookingId, Integer customerId, Integer shopId, LocalDateTime datetime, String status, String paymentStatus, BigDecimal totalPrice) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.shopId = shopId;
        this.datetime = datetime;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
    }

    // Getters & Setters
    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Integer getShopId() { return shopId; }
    public void setShopId(Integer shopId) { this.shopId = shopId; }

    public LocalDateTime getDatetime() { return datetime; }
    public void setDatetime(LocalDateTime datetime) { this.datetime = datetime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
