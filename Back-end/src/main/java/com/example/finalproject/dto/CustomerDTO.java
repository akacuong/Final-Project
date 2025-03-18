package com.example.finalproject.dto;

import java.time.LocalDate;

public class CustomerDTO {
    private Integer id;
    private LocalDate birthYear;
    private String gender;
    private String hairStyle;
    private Integer point;
    private String imageFile;
    private Integer accountId;

    public CustomerDTO() {}

    public CustomerDTO(Integer id, LocalDate birthYear, String gender, String hairStyle, Integer point, String imageFile, Integer accountId) {
        this.id = id;
        this.birthYear = birthYear;
        this.gender = gender;
        this.hairStyle = hairStyle;
        this.point = point;
        this.imageFile = imageFile;
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(LocalDate birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHairStyle() {
        return hairStyle;
    }

    public void setHairStyle(String hairStyle) {
        this.hairStyle = hairStyle;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
