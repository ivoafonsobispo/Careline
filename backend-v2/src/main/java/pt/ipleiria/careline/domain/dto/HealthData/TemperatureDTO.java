package pt.ipleiria.careline.domain.dto.HealthData;

import pt.ipleiria.careline.domain.dto.PatientDTO;

import java.time.Instant;

public class TemperatureDTO extends HealthDataDTO {
    private Integer temperature;

    public TemperatureDTO() {
    }

    public TemperatureDTO(PatientDTO patient, Instant createdAt, Integer temperature) {
        super(patient, createdAt);
        this.temperature = temperature;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

}
