package com.example.finalproject.dto;

public class StyleDTO {
    private Long id;
    private String name;
    private Integer agentId;  // ✅ Ensure Long type is used

    // ✅ Constructor
    public StyleDTO(Long id, String name, Integer agentId) {
        this.id = id;
        this.name = name;
        this.agentId = agentId;
    }

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAgentId() { return agentId; }
    public void setAgentId(Integer agentId) { this.agentId = agentId; }
}
