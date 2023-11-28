package pt.ipleiria.careline.domain.dto.HealthData.Responses;

import java.time.Instant;

public class TemperatureResponseDTO extends HealthDataResponseDTO {
    private Float temperature;

    public TemperatureResponseDTO() {
    }

    public TemperatureResponseDTO(Float temperature, Instant createdAt) {
        super(createdAt);
        this.temperature = temperature;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }
}
