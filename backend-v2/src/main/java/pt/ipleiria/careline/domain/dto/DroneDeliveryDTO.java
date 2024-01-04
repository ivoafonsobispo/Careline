package pt.ipleiria.careline.domain.dto;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.entities.embed.Coordinate;

import java.util.List;

@Setter
@Getter
public class DroneDeliveryDTO {
    private List<String> medications;
    private Coordinate coordinate;

    public DroneDeliveryDTO() {
    }

    public DroneDeliveryDTO(List<String> medications, Coordinate coordinate) {
        this.medications = medications;
        this.coordinate = coordinate;
    }
}
