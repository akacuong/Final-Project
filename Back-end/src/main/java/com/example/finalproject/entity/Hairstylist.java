package com.example.finalproject.entity;

import jakarta.persistence.*;

@Entity
public class Hairstylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    private String name;
    private Integer experience;
    private String specialty;
    private String image;

    // ✅ Constructor không có tham số (bắt buộc cho JPA)
    public Hairstylist() {
    }

    // ✅ Constructor đầy đủ tham số
    public Hairstylist(Shop shop, String name, Integer experience, String specialty, String image) {
        this.shop = shop;
        this.name = name;
        this.experience = experience;
        this.specialty = specialty;
        this.image = image;
    }

    // ✅ Getter cho `id`
    public Integer getId() {
        return id;
    }

    public Shop getShop() {
        return shop;
    }

    public String getName() {
        return name;
    }

    public Integer getExperience() {
        return experience;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getImage() {
        return image;
    }

    // ✅ Setter
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
