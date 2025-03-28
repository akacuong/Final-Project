package com.example.finalproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public OrderDetail() {}

    public OrderDetail(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        updateOrderTotalPrice(); // ✅ Cập nhật totalPrice khi tạo OrderDetail
    }

    // ✅ Cập nhật totalPrice trong Order khi thêm OrderDetail
    private void updateOrderTotalPrice() {
        if (order != null) {
            order.recalculateTotalPrice(); // ✅ Gọi hàm tính lại tổng tiền thay vì setTotalPrice
        }
    }

    // ✅ Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) {
        this.order = order;
        updateOrderTotalPrice(); // ✅ Khi thay đổi Order, cập nhật totalPrice
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) {
        this.product = product;
        updateOrderTotalPrice(); // ✅ Khi thay đổi Product, cập nhật totalPrice
    }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        updateOrderTotalPrice(); // ✅ Khi thay đổi Quantity, cập nhật totalPrice
    }
}
