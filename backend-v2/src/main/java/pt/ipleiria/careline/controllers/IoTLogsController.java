package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.IoTLogsDTO;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.entities.TriageEntity;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.IoTLogs;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.TemperatureService;
import pt.ipleiria.careline.services.TriageService;
import pt.ipleiria.careline.utils.CsvGenerator;
import pt.ipleiria.careline.utils.ZipFileGenerator;

import java.util.List;

@RequestMapping("/api/iot/logs")
@RestController
@CrossOrigin
@Slf4j
public class IoTLogsController {

    public IoTLogsController() {
    }

    @PostMapping
    public void create(@RequestBody @Valid IoTLogsDTO logs) {
        if (logs.getType().equals(IoTLogs.INFO)) {
            log.info(logs.getMessage());
        } else if (logs.getType().equals(IoTLogs.WARNING)) {
            log.warn(logs.getMessage());
        } else if (logs.getType().equals(IoTLogs.ERROR)) {
            log.error(logs.getMessage());
        }
    }
}
