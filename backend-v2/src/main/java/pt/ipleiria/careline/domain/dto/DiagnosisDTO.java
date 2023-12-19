package pt.ipleiria.careline.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.ProfessionalResponseDTO;

import java.time.Instant;
import java.util.List;

public class DiagnosisDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("created_at")
    public Instant createdAt;
    List<String> prescriptions;
    private Long id;
    private PatientResponseDTO patient;
    private ProfessionalResponseDTO professional;
    private String diagnosis;

    public DiagnosisDTO() {
    }

    public DiagnosisDTO(PatientResponseDTO patient, ProfessionalResponseDTO professional, String diagnosis, List<String> prescriptions) {
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
