package pt.ipleiria.careline.domain.entities.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Coordinate {
    @Column(name = "departure_latitude")
    private double departureLatitude;

    @Column(name = "departure_longitude")
    private double departureLongitude;

    @Column(name = "arrival_latitude")
    private double arrivalLatitude;

    @Column(name = "arrival_longitude")
    private double arrivalLongitude;

    public Coordinate() {
    }

    public Coordinate(double departureLatitude, double departureLongitude, double arrivalLatitude, double arrivalLongitude) {
        this.departureLatitude = departureLatitude;
        this.departureLongitude = departureLongitude;
        this.arrivalLatitude = arrivalLatitude;
        this.arrivalLongitude = arrivalLongitude;
    }
}