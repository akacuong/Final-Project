package com.example.finalproject.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private String status; // pending, completed, canceled
    @Column(nullable = false)
    private String paymentStatus = "unpaid";

    @Column(nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;  // ✅ Ban đầu là 0, cập nhật sau

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    public Order() {
        this.createdAt = LocalDateTime.now();
        this.totalPrice = BigDecimal.ZERO;
    }

    public Order(Customer customer, String status) {
        this.customer = customer;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.totalPrice = BigDecimal.ZERO;  //
    }

    // ✅ Cập nhật lại tổng tiền của Order
    public void recalculateTotalPrice() {
        this.totalPrice = orderDetails.stream()
                .map(detail -> detail.getProduct().getPrice().multiply(new BigDecimal(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ✅ THÊM phương thức `setTotalPrice()`
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    // ✅ Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        recalculateTotalPrice(); // ✅ Khi thay đổi danh sách OrderDetail, cập nhật totalPrice
    }
}
