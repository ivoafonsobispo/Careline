package pt.ipleiria.careline.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class PatientResponseDTO {
    @JsonProperty("created_at")
    public Instant createdAt;
    private Long id;
    private String name;
    private String email;
    private String nus;

    public PatientResponseDTO() {
    }

    public PatientResponseDTO(Long id, String name, String email, String nus, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nus = nus;
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNus() {
        return nus;
    }

    public void setNus(String nus) {
        this.nus = nus;
    }
}
