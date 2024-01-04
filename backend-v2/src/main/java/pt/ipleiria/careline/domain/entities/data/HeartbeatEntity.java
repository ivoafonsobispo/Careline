package pt.ipleiria.careline.domain.entities.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "heartbeats")
public class HeartbeatEntity extends DataEntity {
    @NotNull(message = "Heartbeat is required")
    @Min(0)
    @Max(220)
    private Integer heartbeat;
}
