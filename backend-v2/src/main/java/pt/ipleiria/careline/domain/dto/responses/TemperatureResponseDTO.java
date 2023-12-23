package pt.ipleiria.careline.domain.dto.responses;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.enums.Severity;

import java.time.Instant;

@Setter
@Getter
public class TemperatureResponseDTO extends DataResponseDTO {
    private Float temperature;

    public TemperatureResponseDTO() {
    }

    public TemperatureResponseDTO(Float temperature, Instant createdAt, Severity severity) {
        super(createdAt,severity);
        this.temperature = temperature;
    }

}
