package com.example.finalproject.dto;

import java.math.BigDecimal;

public class ServiceDTO {
    private Integer serviceId;
    private Integer shopId;
    private String name;
    private BigDecimal price;
    private String status;

    public ServiceDTO() {
    }

    public ServiceDTO(Integer serviceId, Integer shopId, String name, BigDecimal price, String status) {
        this.serviceId = serviceId;
        this.shopId = shopId;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
