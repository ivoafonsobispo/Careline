package pt.ipleiria.careline.domain.entities.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import pt.ipleiria.careline.domain.enums.Severity;
import pt.ipleiria.careline.domain.enums.Tag;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
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

    private Tag tagSeverity;

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

}
