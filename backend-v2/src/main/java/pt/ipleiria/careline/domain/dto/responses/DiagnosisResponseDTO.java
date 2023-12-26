package pt.ipleiria.careline.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class DiagnosisResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("created_at")
    public ZonedDateTime createdAt;
    List<String> prescriptions;
    private Long id;
    private PatientResponseDTO patient;
    private ProfessionalResponseDTO professional;
    private String diagnosis;

    public DiagnosisResponseDTO() {
    }

    public DiagnosisResponseDTO(Long id, PatientResponseDTO patient,
                                ProfessionalResponseDTO professional,
                                String diagnosis, List<String> prescriptions,
                                Instant createdAt) {
        this.id = id;
        this.patient = patient;
        this.professional = professional;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
        this.createdAt = ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
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
