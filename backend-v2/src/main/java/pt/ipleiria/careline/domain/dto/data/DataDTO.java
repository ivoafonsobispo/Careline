package pt.ipleiria.careline.domain.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import pt.ipleiria.careline.domain.dto.PatientDTO;

import java.time.Instant;

public abstract class DataDTO {
    @JsonProperty("created_at")
    public Instant createdAt;

    public Long id;

    private PatientDTO patient;

    public DataDTO() {
    }

    public DataDTO(PatientDTO patient, Instant createdAt) {
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

    public void setId(Long id) {
        this.id = id;
    }
}
