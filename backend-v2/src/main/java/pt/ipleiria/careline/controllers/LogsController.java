package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.LogsDTO;
import pt.ipleiria.careline.domain.enums.Logs;

@RequestMapping("/api/logs")
@RestController
@CrossOrigin
@Slf4j
public class LogsController {

    public LogsController() {
    }

    @PostMapping("/iot")
    public void createIoTLogs(@RequestBody @Valid LogsDTO logs) {
        if (logs.getType().equals(Logs.INFO)) {
            log.info("IoT Scenario: " + logs.getMessage());
        } else if (logs.getType().equals(Logs.WARNING)) {
            log.warn("IoT Scenario: " + logs.getMessage());
        } else if (logs.getType().equals(Logs.ERROR)) {
            log.error("IoT Scenario: " + logs.getMessage());
        }
    }

    @PostMapping("/ios")
    public void createIOSLogs(@RequestBody @Valid LogsDTO logs) {
        if (logs.getType().equals(Logs.INFO)) {
            log.info("iOS: " + logs.getMessage());
        } else if (logs.getType().equals(Logs.WARNING)) {
            log.warn("iOS: " + logs.getMessage());
        } else if (logs.getType().equals(Logs.ERROR)) {
            log.error("iOS: " + logs.getMessage());
        }
    }
}
