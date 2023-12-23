package pt.ipleiria.careline.domain.dto.data;

import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.enums.Severity;

import java.time.Instant;

public class TriageDTO extends DataDTO {
    private Float temperature;
    private Integer heartbeat;
    private String symptoms;
    private Long tagOrder;
    private String tagSeverity;

    public TriageDTO(PatientResponseDTO patient, Instant createdAt, Float temperature, Integer heartbeat, String simptoms, Long tagOrder, String tagSeverity, Severity severity) {
        super(patient, createdAt, severity);
        this.temperature = temperature;
        this.heartbeat = heartbeat;
        this.symptoms = symptoms;
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

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Long getTagOrder() {
        return tagOrder;
    }

    public void setTagOrder(Long tagOrder) {
        this.tagOrder = tagOrder;
    }

    public String getTagSeverity() {
        return tagSeverity;
    }

    public void setTagSeverity(String tagSeverity) {
        this.tagSeverity = tagSeverity;
    }
}
