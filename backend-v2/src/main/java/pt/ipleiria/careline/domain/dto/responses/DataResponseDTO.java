package pt.ipleiria.careline.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DataResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    public DataResponseDTO() {
    }

    public DataResponseDTO(Instant createdAt) {
        this.createdAt = ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault());
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
