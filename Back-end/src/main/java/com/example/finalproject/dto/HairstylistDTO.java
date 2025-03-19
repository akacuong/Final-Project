package com.example.finalproject.dto;

public class HairstylistDTO {
    private Integer stylistId;
    private Integer shopId;
    private String name;
    private Integer experience;
    private String specialty;
    private String image;

    // ✅ Constructor không có tham số (bắt buộc)
    public HairstylistDTO() {
    }

    // ✅ Constructor đầy đủ tham số
    public HairstylistDTO(Integer stylistId, Integer shopId, String name, Integer experience, String specialty, String image) {
        this.stylistId = stylistId;
        this.shopId = shopId;
        this.name = name;
        this.experience = experience;
        this.specialty = specialty;
        this.image = image;
    }

    // ✅ Getter
    public Integer getStylistId() {
        return stylistId;
    }

    public Integer getShopId() {
        return shopId;
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
    public void setStylistId(Integer stylistId) {
        this.stylistId = stylistId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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
