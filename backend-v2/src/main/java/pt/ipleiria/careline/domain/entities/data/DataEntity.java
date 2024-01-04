package pt.ipleiria.careline.domain.entities.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Severity;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class DataEntity {
    @CurrentTimestamp
    @Column(updatable = false, name = "created_at")
    public Instant createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "health_data_id_seq")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    @NotNull(message = "Patient is required")
    private PatientEntity patient;
    private Severity severity;

    public DataEntity() {
    }

    public DataEntity(PatientEntity patient, Severity severity) {
        this.patient = patient;
        this.severity = severity;
    }
}
