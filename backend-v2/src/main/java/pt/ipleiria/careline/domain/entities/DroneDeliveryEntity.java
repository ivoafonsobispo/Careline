package pt.ipleiria.careline.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import pt.ipleiria.careline.domain.entities.embed.Coordinate;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Delivery;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Enumerated(EnumType.STRING)
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

}
