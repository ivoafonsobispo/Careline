package pt.ipleiria.careline.domain.dto.HealthData.Responses;

import java.time.Instant;

public class TemperatureResponseDTO extends HealthDataResponseDTO {
    private Integer temperature;

    public TemperatureResponseDTO() {
    }

    public TemperatureResponseDTO(Integer temperature, Instant createdAt) {
        super(createdAt);
        this.temperature = temperature;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}
