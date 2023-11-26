package pt.ipleiria.careline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.HeartbeatDTO;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.HeartbeatService;

@RequestMapping("/api/patients/{patientId}/heartbeats")
@RestController
public class HeartbeatController {

    private Mapper<HeartbeatEntity, HeartbeatDTO> heartbeatMapper;
    private HeartbeatService heartbeatService;

    public HeartbeatController(Mapper<HeartbeatEntity, HeartbeatDTO> heartbeatMapper, HeartbeatService heartbeatService) {
        this.heartbeatMapper = heartbeatMapper;
        this.heartbeatService = heartbeatService;
    }

    @PostMapping
    public ResponseEntity<HeartbeatDTO> createHeartbeat(@PathVariable("patientId") Integer patientId, @RequestBody HeartbeatDTO heartbeatDTO) {
        HeartbeatEntity heartbeatEntity = heartbeatMapper.mapFrom(heartbeatDTO);
        HeartbeatEntity createdHeartbeat = heartbeatService.createHeartbeat(patientId, heartbeatEntity);
        HeartbeatDTO createdHeartbeatDTO = heartbeatMapper.mapToDTO(createdHeartbeat);
        return new ResponseEntity<>(createdHeartbeatDTO, HttpStatus.CREATED);
    }
}
