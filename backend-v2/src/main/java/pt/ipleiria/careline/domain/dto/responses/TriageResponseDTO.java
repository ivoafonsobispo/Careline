package pt.ipleiria.careline.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.enums.Severity;
import pt.ipleiria.careline.domain.enums.Status;

import java.time.Instant;
import java.time.ZonedDateTime;

@Setter
@Getter
public class TriageResponseDTO extends DataResponseDTO {
    private Long id;
    private PatientResponseDTO patient;
    private Float temperature;
    private Integer heartbeat;
    private String symptoms;
    private Status status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("review_date")
    private ZonedDateTime reviewDate;

    public TriageResponseDTO(Long id, PatientResponseDTO patient, Instant createdAt, Float temperature, Integer heartbeat, String symptoms, Severity severity, Status status, Instant reviewDate) {
        super(createdAt, severity);
        this.id = id;
        this.patient = patient;
        this.temperature = temperature;
        this.heartbeat = heartbeat;
        this.symptoms = symptoms;
        this.status = status;
        this.reviewDate = ZonedDateTime.ofInstant(reviewDate, java.time.ZoneId.systemDefault());
    }

    public TriageResponseDTO() {
    }
}
