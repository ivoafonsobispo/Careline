package pt.ipleiria.careline.domain.dto.data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.enums.Severity;

import java.time.Instant;

public class TriageDTO extends DataDTO {

    private Float temperature;
    private Integer heartbeat;
    private String simptoms;

    public TriageDTO(PatientResponseDTO patient, Instant createdAt, Float temperature, Integer heartbeat, String simptoms, Severity severity) {
        super(patient, createdAt, severity);
        this.temperature = temperature;
        this.heartbeat = heartbeat;
        this.simptoms = simptoms;
    }

    public TriageDTO() {
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getSimptoms() {
        return simptoms;
    }

    public void setSimptoms(String simptoms) {
        this.simptoms = simptoms;
    }
}
