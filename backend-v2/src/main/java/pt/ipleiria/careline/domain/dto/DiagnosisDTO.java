package pt.ipleiria.careline.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.ProfessionalResponseDTO;
import pt.ipleiria.careline.domain.entities.embed.Medication;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class DiagnosisDTO {
    @JsonProperty("created_at")
    private Instant createdAt;
    private List<Medication> medications;
    private Long id;
    private PatientResponseDTO patient;
    private ProfessionalResponseDTO professional;
    private String diagnosis;

    public DiagnosisDTO() {
    }

    public DiagnosisDTO(PatientResponseDTO patient, ProfessionalResponseDTO professional, String diagnosis, List<Medication> medications) {
        this.patient = patient;
        this.professional = professional;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.createdAt = Instant.now();
    }

}
