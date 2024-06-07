package pt.ipleiria.careline.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;

import java.time.Instant;
import java.util.List;

public class ProfessionalDTO {
    @JsonProperty("created_at")
    public Instant createdAt;
    private Long id;
    private String name;
    private String email;
    private String password;
    private String nus;
    private List<PatientResponseDTO> patients;

    public ProfessionalDTO() {
    }

    public ProfessionalDTO(Long id, String name, String email, String password, String nus, Instant createdAt, List<PatientResponseDTO> patients) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nus = nus;
        this.createdAt = createdAt;
        this.patients = patients;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNus() {
        return nus;
    }

    public void setNus(String nus) {
        this.nus = nus;
    }

    public List<PatientResponseDTO> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientResponseDTO> patients) {
        this.patients = patients;
    }
}
