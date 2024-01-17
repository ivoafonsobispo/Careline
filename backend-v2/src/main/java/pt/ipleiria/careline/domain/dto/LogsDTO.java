package pt.ipleiria.careline.domain.dto;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.enums.Logs;

@Getter
@Setter
public class LogsDTO {

    private Logs type;
    private String message;

    public LogsDTO() {
    }

    public LogsDTO(Logs type, String message) {
        this.type = type;
        this.message = message;
    }
}
