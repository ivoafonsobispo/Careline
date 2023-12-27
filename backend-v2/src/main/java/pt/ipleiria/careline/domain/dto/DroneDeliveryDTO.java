package pt.ipleiria.careline.domain.dto;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.entities.embed.Coordinate;

import java.util.List;

@Setter
@Getter
public class DroneDeliveryDTO {
    private List<String> prescriptions;
    private Coordinate coordinate;

    public DroneDeliveryDTO() {
    }

    public DroneDeliveryDTO(List<String> prescriptions, Coordinate coordinate) {
        this.prescriptions = prescriptions;
        this.coordinate = coordinate;
    }
}
