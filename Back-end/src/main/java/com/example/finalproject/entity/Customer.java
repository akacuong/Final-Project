package com.example.finalproject.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "birthyear", nullable = false)
    private LocalDate birthYear;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "hair_style")
    private String hairStyle;

    @Column(name = "point")
    private Integer point;

    @Column(name = "image")
    private String imageFile;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;  // ✅ Many-to-One Relationship

    // Constructors
    public Customer() {}

    public Customer(LocalDate birthYear, String gender, String hairStyle, Integer point, String imageFile, Account account) {
        this.birthYear = birthYear;
        this.gender = gender;
        this.hairStyle = hairStyle;
        this.point = point;
        this.imageFile = imageFile;
        this.account = account;
    }

    // Getters and Setters
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
