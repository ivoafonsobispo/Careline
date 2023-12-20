package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.data.TriageDTO;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Tag;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.TriageService;

import java.util.Optional;

@RequestMapping("/api/triages")
@RestController
public class TriageController {
    private final Mapper<TriageEntity, TriageDTO> triageMapper;
    private final TriageService triageService;
    private final PatientService patientService;

    public TriageController(Mapper<TriageEntity, TriageDTO> triageMapper, TriageService triageService, PatientService patientService) {
        this.triageMapper = triageMapper;
        this.triageService = triageService;
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<TriageDTO> create(@RequestBody @Valid TriageDTO triageDTO) {
            TriageEntity triageEntity = new TriageEntity();
            //Define triage data
            triageEntity.setTemperature(triageDTO.getTemperature());
            triageEntity.setHeartbeat(triageDTO.getHeartbeat());
            triageEntity.setSimptoms(triageDTO.getSimptoms());
            triageEntity.setSeverity(Tag.getTagByName(triageDTO.getSeverity()));
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
            return new ResponseEntity<>(triageMapper.mapToDTO(savedtriageEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<TriageDTO> listTriages(Pageable pageable) {
        Page<TriageEntity> triageEntities = triageService.findAll(pageable);
        return triageEntities.map(triageMapper::mapToDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TriageDTO> getTriageById(@PathVariable("id") Long id) {
        Optional<TriageEntity> triage = triageService.getTriageById(id);
        return triage.map(triageEntity -> {
            TriageDTO triageDTO = triageMapper.mapToDTO(triageEntity);
            return new ResponseEntity<>(triageDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TriageDTO> fullUpdateTriage(@PathVariable("id") Long id, @RequestBody @Valid TriageDTO triageDTO) {
        if (!triageService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        TriageEntity triageEntity = triageService.getTriageById(id).get();
        triageEntity.setSimptoms(triageDTO.getSimptoms());
        triageEntity.setHeartbeat(triageDTO.getHeartbeat());
        triageEntity.setSimptoms(triageDTO.getSimptoms());
        TriageEntity savedTriageEntity = triageService.partialUpdate(id, triageEntity);
        return new ResponseEntity<>(
                triageMapper.mapToDTO(savedTriageEntity), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TriageDTO> partialUpdateTriage(@PathVariable("id") Long id, @RequestBody @Valid TriageDTO triageDTO) {
        if (!triageService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        TriageEntity triageEntity = triageMapper.mapFrom(triageDTO);
        TriageEntity savedTriageEntity = triageService.partialUpdate(id, triageEntity);
        return new ResponseEntity<>(
                triageMapper.mapToDTO(savedTriageEntity), HttpStatus.OK);
    }

    @PatchMapping("/tags")
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProfessional(@PathVariable("id") Long id) {
        if (!triageService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        triageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
