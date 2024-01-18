package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.dto.data.HeartbeatDTO;
import pt.ipleiria.careline.domain.dto.responses.HeartbeatResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.PatientService;

import java.time.Instant;
import java.util.Optional;

@RequestMapping("/api/patients/{patientId}/heartbeats")
@RestController
@CrossOrigin
public class HeartbeatController {

    private final Mapper<HeartbeatEntity, HeartbeatDTO> heartbeatMapper;
    private final Mapper<PatientEntity, PatientDTO> patientMapper;
    private final HeartbeatService heartbeatService;
    private final PatientService patientService;
    private final SimpMessagingTemplate messagingTemplate;
    private Mapper<PatientEntity, PatientResponseDTO> patientResponseDTOMapper;

    public HeartbeatController(Mapper<HeartbeatEntity, HeartbeatDTO> heartbeatMapper, HeartbeatService heartbeatService, PatientService patientService, Mapper<PatientEntity, PatientDTO> patientMapper, SimpMessagingTemplate messagingTemplate) {
        this.heartbeatMapper = heartbeatMapper;
        this.patientMapper = patientMapper;
        this.heartbeatService = heartbeatService;
        this.patientService = patientService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<HeartbeatResponseDTO> create(@PathVariable("patientId") Long patientId, @RequestBody @Valid HeartbeatDTO heartbeatDTO) {
        HeartbeatEntity heartbeatEntity = heartbeatMapper.mapFrom(heartbeatDTO);
        HeartbeatEntity createdHeartbeat = heartbeatService.create(patientId, heartbeatEntity);
        HeartbeatResponseDTO createdHeartbeatDTO = new HeartbeatResponseDTO(createdHeartbeat.getHeartbeat(), Instant.now(), createdHeartbeat.getSeverity());

        messagingTemplate.convertAndSend("/topic/heartbeats", createdHeartbeatDTO);

        return new ResponseEntity<>(createdHeartbeatDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('PATIENT') or hasRole('PROFESSIONAL')")
    public Page<HeartbeatResponseDTO> listHeartbeats(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<HeartbeatEntity> heartbeats = heartbeatService.findAll(pageable, patientId);
        return heartbeats.map(heartbeat -> new HeartbeatResponseDTO(heartbeat.getHeartbeat(), heartbeat.getCreatedAt(), heartbeat.getSeverity()));
    }

    @GetMapping("/latest")
    @PreAuthorize("hasRole('PATIENT') or hasRole('PROFESSIONAL')")
    public Page<HeartbeatResponseDTO> listLatestHeartbeats(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<HeartbeatEntity> heartbeats = heartbeatService.findAllLatest(pageable, patientId);
        return heartbeats.map(heartbeat -> new HeartbeatResponseDTO(heartbeat.getHeartbeat(), heartbeat.getCreatedAt(), heartbeat.getSeverity()));
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('PROFESSIONAL')")
    public Page<HeartbeatResponseDTO> listHeartbeatsByDate(@PathVariable("patientId") Long patientId, @PathVariable("date") String date, Pageable pageable) {
        Page<HeartbeatEntity> heartbeats = heartbeatService.findAllByDate(pageable, patientId, date);
        return heartbeats.map(heartbeat -> new HeartbeatResponseDTO(heartbeat.getHeartbeat(), heartbeat.getCreatedAt(), heartbeat.getSeverity()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('PROFESSIONAL')")
    public ResponseEntity<HeartbeatDTO> getHeartbeatById(@PathVariable("id") Long id) {
        Optional<HeartbeatEntity> heartbeat = heartbeatService.getById(id);
        return heartbeat.map(heartbeatEntity -> {
            HeartbeatDTO heartbeatDTO = heartbeatMapper.mapToDTO(heartbeatEntity);
            return new ResponseEntity<>(heartbeatDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT') or hasRole('PROFESSIONAL')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (!heartbeatService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        heartbeatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
