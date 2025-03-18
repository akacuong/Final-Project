package com.example.finalproject.dto;

public class AgentDTO {
    private Integer id;  // ✅ Changed back to Integer
    private String specialization;
    private String location;
    private String establishment;
    private String openingHours;
    private String professionalSkills;
    private String ownerName;
    private String agentName;
    private String email;
    private Integer accountId;  // ✅ Changed back to Integer

    public AgentDTO() {}

    // ✅ Fixed Constructor Mismatch
    public AgentDTO(Integer id, String specialization, String location, String establishment,
                    String openingHours, String professionalSkills, String ownerName,
                    String agentName, String email, Integer accountId) { // ✅ Fixed constructor
        this.id = id;
        this.specialization = specialization;
        this.location = location;
        this.establishment = establishment;
        this.openingHours = openingHours;
        this.professionalSkills = professionalSkills;
        this.ownerName = ownerName;
        this.agentName = agentName;
        this.email = email;
        this.accountId = accountId;
    }

    // ✅ Getters & Setters with Integer IDs
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getEstablishment() { return establishment; }
    public void setEstablishment(String establishment) { this.establishment = establishment; }

    public String getOpeningHours() { return openingHours; }
    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }

    public String getProfessionalSkills() { return professionalSkills; }
    public void setProfessionalSkills(String professionalSkills) { this.professionalSkills = professionalSkills; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { this.accountId = accountId; }
}
