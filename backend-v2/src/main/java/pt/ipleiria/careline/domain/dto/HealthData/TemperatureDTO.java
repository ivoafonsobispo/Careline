package pt.ipleiria.careline.domain.dto.HealthData;

import pt.ipleiria.careline.domain.dto.PatientDTO;

import java.time.Instant;

public class TemperatureDTO extends HealthDataDTO {
    private Float temperature;

    public TemperatureDTO() {
    }

    public TemperatureDTO(PatientDTO patient, Instant createdAt, Float temperature) {
        super(patient, createdAt);
        this.temperature = temperature;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

}
