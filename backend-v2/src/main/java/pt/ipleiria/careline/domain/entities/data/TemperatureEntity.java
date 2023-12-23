package pt.ipleiria.careline.domain.entities.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Severity;

@Entity
@Table(name = "temperatures")
public class TemperatureEntity extends DataEntity {
    @NotNull(message = "Temperature is required")
    @Min(25)
    @Max(50)
    private Float temperature;


    public TemperatureEntity() {
    }

    public TemperatureEntity(Float temperature, PatientEntity patient, Severity severity) {
        super(patient,severity);
        this.temperature = temperature;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }
}
