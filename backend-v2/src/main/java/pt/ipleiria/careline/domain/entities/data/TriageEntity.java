package pt.ipleiria.careline.domain.entities.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import pt.ipleiria.careline.domain.enums.Severity;
import pt.ipleiria.careline.domain.enums.Tag;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "triages")
public class TriageEntity extends DataEntity {

    @NotNull
    @Min(25)
    @Max(50)
    private Float temperature;

    @NotNull
    @Min(0)
    @Max(220)
    private Integer heartbeat;

    private String symptoms;

    @NotNull
    private Long tagOrder;

    private Tag severity;

    @ManyToMany
    @JoinTable(
            name = "triage_professional",
            joinColumns = @JoinColumn(name = "triage_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id"))
    private List<ProfessionalEntity> professionals;
    
    public TriageEntity() {
    }

    public TriageEntity(PatientEntity patient, Float temperature, Integer heartbeat, String simptoms, Long tagOrder, Severity severity) {
        super(patient,severity);
        this.temperature = temperature;
        this.heartbeat = heartbeat;
        this.symptoms = symptoms;
        this.professionals = new ArrayList<ProfessionalEntity>();
        this.tagOrder = tagOrder;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public void setProfessionals(List<ProfessionalEntity> professionals) {
        this.professionals = professionals;
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

    public Tag getSeverity() {
        return severity;
    }

    public void setSeverity(Tag severity) {
        this.severity = severity;
    }

    public List<ProfessionalEntity> getProfessionals() {
        return professionals;
    }
}
