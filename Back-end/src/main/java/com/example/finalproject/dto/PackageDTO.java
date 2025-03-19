package com.example.finalproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PackageDTO {
    private Integer id;
    private Integer agentId;
    private String name;
    private BigDecimal price;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructors
    public PackageDTO() {}

    public PackageDTO(Integer id, Integer agentId, String name, BigDecimal price, String status, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.agentId = agentId;
        this.name = name;
        this.price = price;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
