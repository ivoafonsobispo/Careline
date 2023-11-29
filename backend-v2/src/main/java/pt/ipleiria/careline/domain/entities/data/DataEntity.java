package pt.ipleiria.careline.domain.entities.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CurrentTimestamp;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;

import java.time.Instant;

@MappedSuperclass
public abstract class DataEntity {
    @CurrentTimestamp
    @Column(name = "created_at")
    public Instant createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "health_data_id_seq")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    @NotNull(message = "Patient is required")
    private PatientEntity patient;

    public DataEntity() {
    }

    public DataEntity(PatientEntity patient) {
        this.patient = patient;
        this.createdAt = Instant.now();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
}
