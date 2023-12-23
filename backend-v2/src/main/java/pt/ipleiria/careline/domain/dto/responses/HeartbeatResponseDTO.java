package pt.ipleiria.careline.domain.dto.responses;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.enums.Severity;

import java.time.Instant;

@Setter
@Getter
public class HeartbeatResponseDTO extends DataResponseDTO {
    private Integer heartbeat;

    public HeartbeatResponseDTO() {
    }

    public HeartbeatResponseDTO(Integer heartbeat, Instant createdAt, Severity severity) {
        super(createdAt,severity);
        this.heartbeat = heartbeat;
    }

}
