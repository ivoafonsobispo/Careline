package pt.ipleiria.careline.domain.dto.data;

import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;

import java.time.Instant;

public class TriageDTO extends DataDTO {
    private Float temperature;
    private Integer heartbeat;
    private String symptoms;
    private Long tagOrder;
    private String severity;

    public TriageDTO( PatientResponseDTO patient, Instant createdAt, Float temperature, Integer heartbeat, String symptoms, Long tagOrder) {
        super(patient, createdAt);
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

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
