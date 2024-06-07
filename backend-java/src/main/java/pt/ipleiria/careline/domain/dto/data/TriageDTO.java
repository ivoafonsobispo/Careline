package pt.ipleiria.careline.domain.dto.data;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.enums.Severity;
import pt.ipleiria.careline.domain.enums.Status;

import java.time.Instant;

@Setter
@Getter
public class TriageDTO extends DataDTO {
    private Float temperature;
    private Integer heartbeat;
    private String symptoms;
    private Status status;
    private Instant reviewDate;

    public TriageDTO(PatientResponseDTO patient, Instant createdAt, Float temperature, Integer heartbeat, String symptoms, Severity severity, Status status, Instant reviewDate) {
        super(patient, createdAt, severity);
        this.temperature = temperature;
        this.heartbeat = heartbeat;
        this.symptoms = symptoms;
        this.status = status;
        this.reviewDate = reviewDate;
    }

    public TriageDTO() {
    }
}
