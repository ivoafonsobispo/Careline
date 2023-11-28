package pt.ipleiria.careline.domain.entities.healthdata;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;

@Entity
@Table(name = "temperatures")
public class TemperatureEntity extends HealthDataEntity {
    @NotNull
    @Min(25)
    @Max(50)
    private Float temperature;


    public TemperatureEntity() {
    }

    public TemperatureEntity(Float temperature, PatientEntity patient) {
        super(patient);
        this.temperature = temperature;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }
}
