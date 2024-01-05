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
@Table(name = "temperatures")
public class TemperatureEntity extends DataEntity {
    @NotNull(message = "Temperature is required")
    @Min(25)
    @Max(50)
    private Float temperature;
}
