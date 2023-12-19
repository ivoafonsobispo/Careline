package pt.ipleiria.careline.domain.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;

import java.time.Instant;

public abstract class DataDTO {
    @JsonProperty("created_at")
    public Instant createdAt;

    private PatientResponseDTO patient;


    public DataDTO() {
    }

    public DataDTO(PatientResponseDTO patient, Instant createdAt) {
        this.patient = patient;
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public PatientResponseDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientResponseDTO patient) {
        this.patient = patient;
    }
}
