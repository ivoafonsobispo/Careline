package pt.ipleiria.careline.domain.dto.HealthData;

import pt.ipleiria.careline.domain.dto.PatientDTO;

import java.time.Instant;

public class HeartbeatDTO extends HealthDataDTO {
    private Integer heartbeat;


    public HeartbeatDTO() {
    }

    public HeartbeatDTO(PatientDTO patient, Instant createdAt, Integer heartbeat) {
        super(patient, createdAt);
        this.heartbeat = heartbeat;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

}
