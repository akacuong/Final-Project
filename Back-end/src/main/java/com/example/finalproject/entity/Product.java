package com.example.finalproject.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String image;
    private String name;

    @Column(nullable = false)
    private BigDecimal price;  // ✅ Đảm bảo price là BigDecimal

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() {}

    public Product(String image, String name, BigDecimal price, Category category) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // ✅ Getters & Setters
    public Integer getId() { return id; }
    public String getImage() { return image; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }  // ✅ Trả về BigDecimal
    public Category getCategory() { return category; }

    public void setId(Integer id) { this.id = id; }
    public void setImage(String image) { this.image = image; }
    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }  // ✅ Nhận BigDecimal
    public void setCategory(Category category) { this.category = category; }
}
