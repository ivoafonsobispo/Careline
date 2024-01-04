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
import pt.ipleiria.careline.domain.enums.Status;

import java.time.Instant;
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
    private Status status;
    @Column(name = "review_date")
    private Instant reviewDate;


    @ManyToMany
    @JoinTable(
            name = "triage_professional",
            joinColumns = @JoinColumn(name = "triage_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id"))
    private List<ProfessionalEntity> professionals;

    public TriageEntity() {
    }

    public TriageEntity(PatientEntity patient, Float temperature, Integer heartbeat, String symptoms, Severity severity, Status status, Instant reviewDate) {
        super(patient, severity);
        this.temperature = temperature;
        this.heartbeat = heartbeat;
        this.symptoms = symptoms;
        this.status = status;
        this.reviewDate = reviewDate;
        this.professionals = new ArrayList<ProfessionalEntity>();
    }

}
