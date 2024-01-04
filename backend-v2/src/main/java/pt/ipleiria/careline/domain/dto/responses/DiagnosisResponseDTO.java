package pt.ipleiria.careline.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.entities.embed.Medication;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
public class DiagnosisResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
    private List<Medication> medications;
    private Long id;
    private PatientResponseDTO patient;
    private ProfessionalResponseDTO professional;
    private String diagnosis;

    public DiagnosisResponseDTO() {
    }

    public DiagnosisResponseDTO(Long id, PatientResponseDTO patient,
                                ProfessionalResponseDTO professional,
                                String diagnosis, List<Medication> medications,
                                Instant createdAt) {
        this.id = id;
        this.patient = patient;
        this.professional = professional;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.createdAt = ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }

}
