package pt.ipleiria.careline.entities.healthdata;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.entities.users.Patient;

import java.sql.Date;

@Entity
public class Temperature extends HealthData {
    @NotNull
    private Integer temperature;

    public Temperature() {
    }

    public Temperature(Patient patient, Date created_at, Integer temperature) {
        super(patient, created_at);
        this.temperature = temperature;
    }

    public Temperature(Integer id, Patient patient, Date created_at, Integer temperature) {
        super(id, patient, created_at);
        this.temperature = temperature;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}
