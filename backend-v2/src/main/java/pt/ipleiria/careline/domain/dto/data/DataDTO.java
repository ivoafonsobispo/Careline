package pt.ipleiria.careline.domain.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.enums.Severity;

import java.time.Instant;

@Setter
@Getter
public abstract class DataDTO {
    @JsonProperty("created_at")
    public Instant createdAt;
    private PatientResponseDTO patient;
    private Severity severity;

    public DataDTO() {
    }

    public DataDTO(PatientResponseDTO patient, Instant createdAt, Severity severity) {
        this.patient = patient;
        this.createdAt = createdAt;
        this.severity = severity;
    }

}
