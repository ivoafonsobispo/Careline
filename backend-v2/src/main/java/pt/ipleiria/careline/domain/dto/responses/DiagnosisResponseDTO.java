package pt.ipleiria.careline.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class DiagnosisResponseDTO {
    @JsonProperty("created_at")
    public Instant createdAt;
    List<String> prescriptions;
    private Long id;
    private PatientResponseDTO patient;
    private ProfessionalResponseDTO professional;
    private String diagnosis;

    public DiagnosisResponseDTO() {
    }

    public DiagnosisResponseDTO(PatientResponseDTO patient, ProfessionalResponseDTO professional, String diagnosis, List<String> prescriptions) {
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

    public PatientResponseDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientResponseDTO patient) {
        this.patient = patient;
    }

    public ProfessionalResponseDTO getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalResponseDTO professional) {
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
