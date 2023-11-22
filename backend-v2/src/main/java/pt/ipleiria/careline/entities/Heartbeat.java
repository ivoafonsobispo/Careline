package pt.ipleiria.careline.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@Entity
public class Heartbeat extends HealthData {
    @NotNull
    private Integer heartbeat;

    public Heartbeat() {
    }

    public Heartbeat(Patient patient, @NotNull Integer heartbeat, Date created_at) {
        super(patient, created_at);
        this.heartbeat = heartbeat;
    }

    public Heartbeat(Integer id, Patient patient, @NotNull Integer heartbeat, Date created_at) {
        super(id, patient, created_at);
        this.heartbeat = heartbeat;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }
}
