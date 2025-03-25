package com.example.finalproject.dto;

import java.math.BigDecimal;

public class OrderDetailDTO {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal totalPrice;

    public OrderDetailDTO() {}

    public OrderDetailDTO(Integer id, Integer orderId, Integer productId, Integer quantity, BigDecimal totalPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
