package pt.ipleiria.careline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.HeartbeatDTO;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.HeartbeatService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping
    public List<HeartbeatDTO> listHeartbeats() {
        List<HeartbeatEntity> heartbeats = heartbeatService.findAll();
        return heartbeats.stream().map(heartbeatMapper::mapToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeartbeatDTO> getHeartbeatById(@PathVariable("id") Long id) {
        Optional<HeartbeatEntity> heartbeat = heartbeatService.getHeartbeatById(id);
        return heartbeat.map(heartbeatEntity -> {
            HeartbeatDTO heartbeatDTO = heartbeatMapper.mapToDTO(heartbeatEntity);
            return new ResponseEntity<>(heartbeatDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
