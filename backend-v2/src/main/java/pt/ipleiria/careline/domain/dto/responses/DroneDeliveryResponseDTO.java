package pt.ipleiria.careline.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.entities.embed.Coordinate;
import pt.ipleiria.careline.domain.enums.Delivery;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
public class DroneDeliveryResponseDTO {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
    private PatientResponseDTO patient;
    private List<String> prescriptions;
    private Delivery status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("departure_time")
    private ZonedDateTime departureTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEEE, MMM dd 'AT' HH:mm", locale = "en_US")
    @JsonProperty("arrival_time")
    private ZonedDateTime arrivalTime;
    private Coordinate coordinate;

    public DroneDeliveryResponseDTO() {
    }

    public DroneDeliveryResponseDTO(Long id, Instant createdAt, PatientResponseDTO patient, List<String> prescriptions, Delivery status, Instant departureTime, Instant arrivalTime, Coordinate coordinate) {
        this.id = id;
        this.createdAt = ZonedDateTime.ofInstant(createdAt, ZoneId.systemDefault());
        this.patient = patient;
        this.prescriptions = prescriptions;
        this.status = status;
        this.departureTime = ZonedDateTime.ofInstant(departureTime, ZoneId.systemDefault());
        this.arrivalTime = ZonedDateTime.ofInstant(arrivalTime, ZoneId.systemDefault());
        this.coordinate = coordinate;
    }
}
