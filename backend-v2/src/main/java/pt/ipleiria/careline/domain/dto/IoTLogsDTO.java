package pt.ipleiria.careline.domain.dto;

import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.careline.domain.enums.IoTLogs;

@Getter
@Setter
public class IoTLogsDTO {

    private IoTLogs type;
    private String message;

    public IoTLogsDTO() {
    }

    public IoTLogsDTO(IoTLogs type, String message) {
        this.type = type;
        this.message = message;
    }
}
