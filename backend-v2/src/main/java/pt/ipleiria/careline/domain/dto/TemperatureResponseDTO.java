package pt.ipleiria.careline.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TemperatureResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("created_at")
    public ZonedDateTime createdAt;
    private Integer temperature;

    public TemperatureResponseDTO() {
    }

    public TemperatureResponseDTO(Integer temperature, Instant createdAt) {
        this.temperature = temperature;
        this.createdAt = ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}
