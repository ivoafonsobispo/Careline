package pt.ipleiria.careline.domain.dto.HealthData.Responses;

import java.time.Instant;

public class HeartbeatResponseDTO extends HealthDataResponseDTO {
    private Integer heartbeat;

    public HeartbeatResponseDTO() {
    }

    public HeartbeatResponseDTO(Integer heartbeat, Instant createdAt) {
        super(createdAt);
        this.heartbeat = heartbeat;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

}
