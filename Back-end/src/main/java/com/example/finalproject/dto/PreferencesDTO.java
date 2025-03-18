package com.example.finalproject.dto;

public class PreferencesDTO {
    private Integer id;
    private Integer customerId;
    private String name;

    // Constructors
    public PreferencesDTO() {}

    public PreferencesDTO(Integer id, Integer customerId, String name) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
