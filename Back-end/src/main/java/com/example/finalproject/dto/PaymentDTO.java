package com.example.finalproject.dto;

import java.time.LocalDateTime;

public class PaymentDTO {
    private Integer paymentId;
    private String method;
    private LocalDateTime transactionDate;
    private Integer orderId;
    private Integer bookingId;

    // Bổ sung các field mới
    private Integer pointUsed;
    private Integer amountPaidByPoint;
    private Integer amountPaidByCashOrVnpay;

    // Constructor mới
    public PaymentDTO(Integer paymentId, String method, LocalDateTime transactionDate,
                      Integer orderId, Integer bookingId,
                      Integer pointUsed, Integer amountPaidByPoint, Integer amountPaidByCashOrVnpay) {
        this.paymentId = paymentId;
        this.method = method;
        this.transactionDate = transactionDate;
        this.orderId = orderId;
        this.bookingId = bookingId;
        this.pointUsed = pointUsed;
        this.amountPaidByPoint = amountPaidByPoint;
        this.amountPaidByCashOrVnpay = amountPaidByCashOrVnpay;
    }

    // Getter/setter

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getPointUsed() {
        return pointUsed;
    }

    public void setPointUsed(Integer pointUsed) {
        this.pointUsed = pointUsed;
    }

    public Integer getAmountPaidByPoint() {
        return amountPaidByPoint;
    }

    public void setAmountPaidByPoint(Integer amountPaidByPoint) {
        this.amountPaidByPoint = amountPaidByPoint;
    }

    public Integer getAmountPaidByCashOrVnpay() {
        return amountPaidByCashOrVnpay;
    }

    public void setAmountPaidByCashOrVnpay(Integer amountPaidByCashOrVnpay) {
        this.amountPaidByCashOrVnpay = amountPaidByCashOrVnpay;
    }
}
