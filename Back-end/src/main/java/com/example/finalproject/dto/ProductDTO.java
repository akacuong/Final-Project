package com.example.finalproject.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private Integer id;
    private String image;
    private String name;
    private BigDecimal price;
    private Integer categoryId;

    public ProductDTO(Integer id, String image, String name, BigDecimal price, Integer categoryId) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }
    public ProductDTO() {
    }

    public Integer getId() { return id; }
    public String getImage() { return image; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }  // ✅ Sửa kiểu dữ liệu
    public Integer getCategoryId() { return categoryId; }

    public void setId(Integer id) { this.id = id; }
    public void setImage(String image) { this.image = image; }
    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }  // ✅ Sửa kiểu dữ liệu
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
}
