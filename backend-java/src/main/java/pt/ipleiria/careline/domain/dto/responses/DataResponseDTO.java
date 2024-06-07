package pt.ipleiria.careline.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.enums.Severity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Setter
@Getter
public abstract class DataResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    private Severity severity;

    public DataResponseDTO() {
    }

    public DataResponseDTO(Instant createdAt, Severity severity) {
        this.createdAt = ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault());
        this.severity = severity;
    }

}
