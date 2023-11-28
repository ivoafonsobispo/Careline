package pt.ipleiria.careline.domain.dto.HealthData;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.ipleiria.careline.domain.dto.PatientDTO;

import java.time.Instant;

public abstract class HealthDataDTO {
    @JsonProperty("created_at")
    public Instant createdAt;

    private PatientDTO patient;

    public HealthDataDTO() {
    }

    public HealthDataDTO(PatientDTO patient, Instant createdAt) {
        this.patient = patient;
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }
}
