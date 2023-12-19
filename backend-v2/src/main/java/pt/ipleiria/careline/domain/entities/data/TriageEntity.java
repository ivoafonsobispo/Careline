package pt.ipleiria.careline.domain.entities.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

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

    private String simptoms;

    @ManyToMany
    @JoinTable(
            name = "triage_professional",
            joinColumns = @JoinColumn(name = "triage_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id"))
    private List<ProfessionalEntity> professionals;
    
    public TriageEntity() {
    }

    public TriageEntity(PatientEntity patient, Float temperature, Integer heartbeat, String simptoms) {
        super(patient);
        this.temperature = temperature;
        this.heartbeat = heartbeat;
        this.simptoms = simptoms;
        this.professionals = new ArrayList<ProfessionalEntity>();
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

    public String getSimptoms() {
        return simptoms;
    }

    public void setSimptoms(String simptoms) {
        this.simptoms = simptoms;
    }

}
