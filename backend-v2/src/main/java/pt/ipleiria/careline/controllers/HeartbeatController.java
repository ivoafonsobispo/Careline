package pt.ipleiria.careline.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.data.HeartbeatDTO;
import pt.ipleiria.careline.domain.dto.data.responses.HeartbeatResponseDTO;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.HeartbeatService;

import java.util.Optional;

@RequestMapping("/api/patients/{patientId}/heartbeats")
@RestController
public class HeartbeatController {

    private Mapper<HeartbeatEntity, HeartbeatDTO> heartbeatMapper;
    private Mapper<PatientEntity, PatientDTO> patientMapper;
    private HeartbeatService heartbeatService;

    public HeartbeatController(Mapper<HeartbeatEntity, HeartbeatDTO> heartbeatMapper, HeartbeatService heartbeatService, Mapper<PatientEntity, PatientDTO> patientMapper) {
        this.heartbeatMapper = heartbeatMapper;
        this.patientMapper = patientMapper;
        this.heartbeatService = heartbeatService;
    }

    @PostMapping
    public ResponseEntity<HeartbeatDTO> createHeartbeat(@PathVariable("patientId") Long patientId, @RequestBody HeartbeatDTO heartbeatDTO) {
        HeartbeatEntity heartbeatEntity = heartbeatMapper.mapFrom(heartbeatDTO);
        heartbeatEntity.setPatient(patientMapper.mapFrom(heartbeatDTO.getPatient()));
        HeartbeatEntity createdHeartbeat = heartbeatService.create(patientId, heartbeatEntity);
        HeartbeatDTO createdHeartbeatDTO = heartbeatMapper.mapToDTO(createdHeartbeat);
        return new ResponseEntity<>(createdHeartbeatDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public Page<HeartbeatResponseDTO> listHeartbeats(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<HeartbeatEntity> heartbeats = heartbeatService.findAll(pageable, patientId);
        return heartbeats.map(heartbeat -> new HeartbeatResponseDTO(heartbeat.getHeartbeat(), heartbeat.getCreatedAt()));
    }

    @GetMapping("/latest")
    public Page<HeartbeatResponseDTO> listLatestHeartbeats(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<HeartbeatEntity> heartbeats = heartbeatService.findAllLatest(pageable, patientId);
        return heartbeats.map(heartbeat -> new HeartbeatResponseDTO(heartbeat.getHeartbeat(), heartbeat.getCreatedAt()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeartbeatDTO> getHeartbeatById(@PathVariable("id") Long id) {
        Optional<HeartbeatEntity> heartbeat = heartbeatService.getById(id);
        return heartbeat.map(heartbeatEntity -> {
            HeartbeatDTO heartbeatDTO = heartbeatMapper.mapToDTO(heartbeatEntity);
            return new ResponseEntity<>(heartbeatDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (!heartbeatService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        heartbeatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
