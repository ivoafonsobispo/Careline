package pt.ipleiria.careline.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class TemperatureDTO {
    @JsonProperty("created_at")
    public Instant createdAt;
    private Integer temperature;

    private PatientDTO patient;

    public TemperatureDTO() {
    }

    public TemperatureDTO(Integer temperature, PatientDTO patient, Instant createdAt) {
        this.temperature = temperature;
        this.patient = patient;
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }
}
