package pt.ipleiria.careline.domain.entities.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;

@Entity
@Table(name = "heartbeats")
public class HeartbeatEntity extends DataEntity {
    @NotNull(message = "Heartbeat is required")
    @Min(0)
    @Max(220)
    private Integer heartbeat;

    public HeartbeatEntity() {
    }

    public HeartbeatEntity(Integer heartbeat, PatientEntity patient) {
        super(patient);
        this.heartbeat = heartbeat;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }
}
