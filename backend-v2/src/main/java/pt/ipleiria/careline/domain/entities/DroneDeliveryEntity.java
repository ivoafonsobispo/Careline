package pt.ipleiria.careline.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import pt.ipleiria.careline.domain.entities.embed.Coordinate;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Delivery;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "drone_delivery")
public class DroneDeliveryEntity {
    @CurrentTimestamp
    @Column(name = "created_at")
    public Instant createdAt;
    @NotNull
    List<String> medications;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "drone_delivery_id_seq")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @NotNull
    private Delivery deliveryStatus;
    private Instant departureTime;
    private Instant arrivalTime;
    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "departureLatitude", column = @Column(name = "departure_latitude")),
            @AttributeOverride(name = "departureLongitude", column = @Column(name = "departure_longitude")),
            @AttributeOverride(name = "arrivalLatitude", column = @Column(name = "arrival_latitude")),
            @AttributeOverride(name = "arrivalLongitude", column = @Column(name = "arrival_longitude"))
    })
    private Coordinate coordinate;

    public DroneDeliveryEntity() {
    }

    public DroneDeliveryEntity(Instant createdAt, Long id, PatientEntity patient, List<String> medications, Delivery deliveryStatus, Instant departureTime, Instant arrivalTime, Coordinate coordinate) {
        this.createdAt = createdAt;
        this.id = id;
        this.patient = patient;
        this.medications = medications;
        this.deliveryStatus = deliveryStatus;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.coordinate = coordinate;
    }
}
