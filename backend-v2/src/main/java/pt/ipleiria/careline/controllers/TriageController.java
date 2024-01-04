package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.data.TriageDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.TriageResponseDTO;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.TriageService;

import java.util.Optional;

@RequestMapping("/api")
@RestController
public class TriageController {
    private final Mapper<TriageEntity, TriageDTO> triageMapper;
    private final Mapper<TriageEntity, TriageResponseDTO> triageResponseMapper;
    private final Mapper<PatientEntity, PatientResponseDTO> patientResponseMapper;
    private final TriageService triageService;
    private final PatientService patientService;
    private final SimpMessagingTemplate messagingTemplate;

    public TriageController(Mapper<TriageEntity, TriageDTO> triageMapper, Mapper<TriageEntity, TriageResponseDTO> triageResponseMapper, Mapper<PatientEntity, PatientResponseDTO> patientResponseMapper, TriageService triageService, PatientService patientService, SimpMessagingTemplate messagingTemplate) {
        this.triageMapper = triageMapper;
        this.triageService = triageService;
        this.patientService = patientService;
        this.messagingTemplate = messagingTemplate;
        this.triageResponseMapper = triageResponseMapper;
        this.patientResponseMapper = patientResponseMapper;
    }

    @PostMapping("/patients/{patientId}/triages")
    public ResponseEntity<TriageResponseDTO> create(@RequestBody @Valid TriageDTO triageDTO, @PathVariable("patientId") Long patientId) {
        TriageEntity triageEntity = triageMapper.mapFrom(triageDTO);
        TriageEntity savedTriageEntity = triageService.save(triageEntity, patientId);

        PatientResponseDTO patientResponseDTO = patientResponseMapper.mapToDTO(savedTriageEntity.getPatient());
        TriageResponseDTO createdTriageDTO = new TriageResponseDTO(patientResponseDTO, savedTriageEntity.getCreatedAt(), savedTriageEntity.getTemperature(), savedTriageEntity.getHeartbeat(), savedTriageEntity.getSymptoms(), savedTriageEntity.getSeverity(), savedTriageEntity.getStatus(), savedTriageEntity.getReviewDate());

        messagingTemplate.convertAndSend("/topic/triages", createdTriageDTO);

        return new ResponseEntity<>(createdTriageDTO, HttpStatus.CREATED);
    }

    @GetMapping("/triages")
    public Page<TriageResponseDTO> listTriages(Pageable pageable) {
        Page<TriageEntity> triageEntities = triageService.findAll(pageable);
        return triageEntities.map(triageEntity -> new TriageResponseDTO(patientResponseMapper.mapToDTO(triageEntity.getPatient()), triageEntity.getCreatedAt(), triageEntity.getTemperature(), triageEntity.getHeartbeat(), triageEntity.getSymptoms(), triageEntity.getSeverity(), triageEntity.getStatus(), triageEntity.getReviewDate()));
    }

    @GetMapping("/triages/latest")
    public Page<TriageResponseDTO> listLatestTriages(Pageable pageable) {
        Page<TriageEntity> triageEntities = triageService.findAllLatest(pageable);
        return triageEntities.map(triageEntity -> new TriageResponseDTO(patientResponseMapper.mapToDTO(triageEntity.getPatient()), triageEntity.getCreatedAt(), triageEntity.getTemperature(), triageEntity.getHeartbeat(), triageEntity.getSymptoms(), triageEntity.getSeverity(), triageEntity.getStatus(), triageEntity.getReviewDate()));
    }

    @GetMapping("/triages/unreviewed")
    public Page<TriageResponseDTO> listUnreviewedTriages(Pageable pageable) {
        Page<TriageEntity> triageEntities = triageService.findAllUnreviewed(pageable);
        return triageEntities.map(triageEntity -> new TriageResponseDTO(patientResponseMapper.mapToDTO(triageEntity.getPatient()), triageEntity.getCreatedAt(), triageEntity.getTemperature(), triageEntity.getHeartbeat(), triageEntity.getSymptoms(), triageEntity.getSeverity(), triageEntity.getStatus(), triageEntity.getReviewDate()));
    }

    @GetMapping("/triages/date/{date}")
    public Page<TriageResponseDTO> listLatestTriages(@PathVariable("date") String date, Pageable pageable) {
        Page<TriageEntity> triageEntities = triageService.findAllByDate(pageable, date);
        return triageEntities.map(triageEntity -> new TriageResponseDTO(patientResponseMapper.mapToDTO(triageEntity.getPatient()), triageEntity.getCreatedAt(), triageEntity.getTemperature(), triageEntity.getHeartbeat(), triageEntity.getSymptoms(), triageEntity.getSeverity(), triageEntity.getStatus(), triageEntity.getReviewDate()));
    }

    @GetMapping("/triages/{id}")
    public ResponseEntity<TriageResponseDTO> getTriageById(@PathVariable("id") Long id) {
        Optional<TriageEntity> triage = triageService.getTriageById(id);
        return triage.map(savedTriageEntity -> {
            PatientResponseDTO patientResponseDTO = patientResponseMapper.mapToDTO(savedTriageEntity.getPatient());
            TriageResponseDTO triageDTO = new TriageResponseDTO(patientResponseDTO, savedTriageEntity.getCreatedAt(), savedTriageEntity.getTemperature(), savedTriageEntity.getHeartbeat(), savedTriageEntity.getSymptoms(), savedTriageEntity.getSeverity(), savedTriageEntity.getStatus(), savedTriageEntity.getReviewDate());
            return new ResponseEntity<>(triageDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/triages/{id}")
    public ResponseEntity<TriageDTO> fullUpdateTriage(@PathVariable("id") Long id, @RequestBody @Valid TriageDTO triageDTO) {
        if (!triageService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        TriageEntity triageEntity = triageService.getTriageById(id).get();
        triageEntity.setSymptoms(triageDTO.getSymptoms());
        triageEntity.setHeartbeat(triageDTO.getHeartbeat());
        triageEntity.setSymptoms(triageDTO.getSymptoms());
        TriageEntity savedTriageEntity = triageService.partialUpdate(id, triageEntity);
        return new ResponseEntity<>(
                triageMapper.mapToDTO(savedTriageEntity), HttpStatus.OK);
    }

    @PatchMapping("/triages/{id}")
    public ResponseEntity<TriageDTO> partialUpdateTriage(@PathVariable("id") Long id, @RequestBody @Valid TriageDTO triageDTO) {
        if (!triageService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        TriageEntity triageEntity = triageMapper.mapFrom(triageDTO);
        TriageEntity savedTriageEntity = triageService.partialUpdate(id, triageEntity);
        return new ResponseEntity<>(
                triageMapper.mapToDTO(savedTriageEntity), HttpStatus.OK);
    }

    @PatchMapping("/triages/tags")
    public ResponseEntity resetTagOrder() {
        Optional<TriageEntity> t = triageService.findLastTriage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tese is the first triage in line");
    }

    @PatchMapping("patients/{patientId}/triages/{id}/reviewed")
    public ResponseEntity<TriageResponseDTO> setTriageReviewed(@PathVariable("patientId") Long patientId, @PathVariable("id") Long triageId) {
        TriageEntity triage = triageService.setTriageReviewed(patientId, triageId);
        PatientResponseDTO patientResponseDTO = patientResponseMapper.mapToDTO(triage.getPatient());
        TriageResponseDTO triageResponseDTO = new TriageResponseDTO(patientResponseDTO, triage.getCreatedAt(), triage.getTemperature(), triage.getHeartbeat(), triage.getSymptoms(), triage.getSeverity(), triage.getStatus(), triage.getReviewDate());
        return new ResponseEntity<>(triageResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/triages/{id}")
    public ResponseEntity deleteProfessional(@PathVariable("id") Long id) {
        if (!triageService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        triageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
