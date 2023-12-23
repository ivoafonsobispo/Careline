package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.data.TriageDTO;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Tag;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.TriageService;

import java.util.Optional;

@RequestMapping("/api")
@RestController
public class TriageController {
    private final Mapper<TriageEntity, TriageDTO> triageMapper;
    private final TriageService triageService;
    private final PatientService patientService;
    private SimpMessagingTemplate messagingTemplate;

    public TriageController(Mapper<TriageEntity, TriageDTO> triageMapper,
                            TriageService triageService,
                            PatientService patientService, SimpMessagingTemplate messagingTemplate) {
        this.triageMapper = triageMapper;
        this.triageService = triageService;
        this.patientService = patientService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/triages")
    public ResponseEntity<TriageDTO> create(@RequestBody @Valid TriageDTO triageDTO) {
            TriageEntity triageEntity = new TriageEntity();
            //Define triage data
            triageEntity.setTemperature(triageDTO.getTemperature());
            triageEntity.setHeartbeat(triageDTO.getHeartbeat());
            triageEntity.setSymptoms(triageDTO.getSymptoms());
            triageEntity.setTagSeverity(Tag.getTagByName(triageDTO.getTagSeverity()));
            Optional<TriageEntity> lastTriage = triageService.findLastTriage();
            if(lastTriage.isPresent())
                triageEntity.setTagOrder(lastTriage.get().getTagOrder()+1);
            if(!lastTriage.isPresent())
                triageEntity.setTagOrder(1L);
            Optional<PatientEntity> patient = patientService.getPatientById(triageDTO.getPatient().getId());
            //Get associated patient
            if(patient.isPresent())
                triageEntity.setPatient(patient.get());
            TriageEntity savedtriageEntity = triageService.save(triageEntity);
            TriageDTO createdtriageDTO = triageMapper.mapToDTO(savedtriageEntity);

            messagingTemplate.convertAndSend("/topic/triages",
                    createdtriageDTO);

            return new ResponseEntity<>(createdtriageDTO, HttpStatus.CREATED);
    }

    @GetMapping("/triages")
    public Page<TriageDTO> listTriages(Pageable pageable) {
        Page<TriageEntity> triageEntities = triageService.findAll(pageable);
        return triageEntities.map(triageMapper::mapToDTO);
    }

    @GetMapping("/triages/{id}")
    public ResponseEntity<TriageDTO> getTriageById(@PathVariable("id") Long id) {
        Optional<TriageEntity> triage = triageService.getTriageById(id);
        return triage.map(triageEntity -> {
            TriageDTO triageDTO = triageMapper.mapToDTO(triageEntity);
            return new ResponseEntity<>(triageDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("patients/{id}/triages")
    public Page<TriageDTO> getTriagesByPatient(Pageable pageable, @PathVariable("id") Long patientId) {
        Optional<PatientEntity> patient = patientService.getPatientById(patientId);
        Page<TriageEntity> triages = triageService.getTriagesByPatient(pageable, patient.get());
        return triages.map(triageMapper::mapToDTO);
    }

    @GetMapping("patients/{id}/triage")
    public ResponseEntity<TriageDTO> getLastPatientTriage(@PathVariable("id") Long patientId) {
        Optional<PatientEntity> patient = patientService.getPatientById(patientId);
        Optional<TriageEntity> triage = triageService.findLastParientTriage(patient.get());
        return new ResponseEntity<>(triageMapper.mapToDTO(triage.get()), HttpStatus.OK);
    }

    @GetMapping("patients/{id}/triages/{triageId}")
    public TriageDTO getTriagesByPatient(Pageable pageable, @PathVariable("id") Long patientId, @PathVariable("triageId") Long triageId ) {
        Optional<PatientEntity> patient = patientService.getPatientById(patientId);
        Optional<TriageEntity> triage = triageService.getTriageByPatient(patient.get(),triageId);
        return triageMapper.mapToDTO(triage.get());

    }


    @PutMapping("/triages/{id}")
    public ResponseEntity<TriageDTO> fullUpdateTriage(@PathVariable("id") Long id, @RequestBody @Valid TriageDTO triageDTO) {
        if (!triageService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        TriageEntity triageEntity = triageService.getTriageById(id).get();
        triageEntity.setSymptoms(triageDTO.getSymptoms());
        triageEntity.setHeartbeat(triageDTO.getHeartbeat());
        triageEntity.setSymptoms(triageDTO.getSymptoms());
        triageEntity.setTagSeverity(Tag.getTagByName(triageDTO.getTagSeverity()));
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
        if (t.isPresent()) {
            t.get().setTagOrder(1L);
            TriageEntity savedTriageEntity = triageService.partialUpdate(t.get().getId(), t.get());
            return new ResponseEntity<>(
                    triageMapper.mapToDTO(savedTriageEntity), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tese is the first triage in line");
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
