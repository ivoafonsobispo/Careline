package pt.ipleiria.careline.domain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CurrentTimestamp;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "diagnosis")
public class DiagnosisEntity {
    @CurrentTimestamp
    @Column(name = "created_at")
    public Instant createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "diagnosis_id_seq")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "professional_id")
    private ProfessionalEntity professional;

    @NotNull(message = "Diagnosis is required")
    private String diagnosis;

    List<String> prescriptions;

    public DiagnosisEntity() {
    }

    public DiagnosisEntity(PatientEntity patient, ProfessionalEntity professional, String diagnosis, List<String> prescriptions) {
        this.patient = patient;
        this.professional = professional;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
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

    public ProfessionalEntity getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalEntity professional) {
        this.professional = professional;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
