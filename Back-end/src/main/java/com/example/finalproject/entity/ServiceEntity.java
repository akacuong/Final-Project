package com.example.finalproject.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "service")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    private String name;
    private BigDecimal price;  // ✅ Đổi từ Double → BigDecimal
    private String status;

    // ✅ Constructor
    public ServiceEntity() {}

    public ServiceEntity(Shop shop, String name, BigDecimal price, String status) {
        this.shop = shop;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    // ✅ Getter & Setter
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {   // ✅ Getter BigDecimal
        return price;
    }

    public void setPrice(BigDecimal price) {  // ✅ Setter BigDecimal
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
