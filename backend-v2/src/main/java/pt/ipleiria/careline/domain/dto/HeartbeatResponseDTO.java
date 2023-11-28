package pt.ipleiria.careline.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class HeartbeatResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
    private Integer heartbeat;

    public HeartbeatResponseDTO() {
    }

    public HeartbeatResponseDTO(Integer heartbeat, Instant createdAt) {
        this.heartbeat = heartbeat;
        this.createdAt = ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

}
