package pt.ipleiria.careline.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class HeartbeatDTO {
    @JsonProperty("created_at")
    public Instant createdAt;
    private Integer heartbeat;

    private PatientDTO patient;

    public HeartbeatDTO() {
    }

    public HeartbeatDTO(Integer heartbeat, PatientDTO patient, Instant createdAt) {
        this.heartbeat = heartbeat;
        this.patient = patient;
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }
}
