package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.ProfessionalDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.ProfessionalResponseDTO;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.ProfessionalService;

import java.util.Optional;

@RequestMapping("/api/professionals")
@RestController
@CrossOrigin
public class ProfessionalController {

    private final ProfessionalService professionalService;
    private final Mapper<ProfessionalEntity, ProfessionalDTO> professionalMapper;
    private final Mapper<ProfessionalEntity, ProfessionalResponseDTO> professionalResponseMapper;
    private final Mapper<PatientEntity, PatientResponseDTO> patientResponseMapper;

    public ProfessionalController(ProfessionalService professionalService, Mapper<ProfessionalEntity, ProfessionalDTO> professionalMapper, Mapper<ProfessionalEntity, ProfessionalResponseDTO> professionalResponseMapper, Mapper<PatientEntity, PatientResponseDTO> patientResponseMapper) {
        this.professionalService = professionalService;
        this.professionalMapper = professionalMapper;
        this.professionalResponseMapper = professionalResponseMapper;
        this.patientResponseMapper = patientResponseMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ProfessionalResponseDTO> create(@RequestBody @Valid ProfessionalDTO professionalDTO) {
        ProfessionalEntity professionalEntity = professionalMapper.mapFrom(professionalDTO);
        ProfessionalEntity savedProfessionalEntity = professionalService.save(professionalEntity);
        return new ResponseEntity<>(professionalResponseMapper.mapToDTO(savedProfessionalEntity), HttpStatus.CREATED);
    }

    @PatchMapping("{professionalId}/patients/{patientId}") // Set Patient to Professional
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity associateProfessionalToPatient(@PathVariable("professionalId") Long professionalId, @PathVariable("patientId") Long patientId) {
        professionalService.setPatientToProfessional(professionalId, patientId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("{professionalId}/patients") // Patients without Professional
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Page<PatientResponseDTO> getProfessionalPatients(@PathVariable("professionalId") Long professionalId, Pageable pageable) {
        Page<PatientEntity> patientEntities = professionalService.getProfessionalPatients(professionalId, pageable);
        return patientEntities.map(patientResponseMapper::mapToDTO);
    }

    @GetMapping("{professionalId}/patients/{patientId}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public PatientResponseDTO getProfessionalGetPatients(@PathVariable("professionalId") Long professionalId, @PathVariable("patientId") Long patientId) {
        PatientEntity patient = professionalService.getPatientById(professionalId, patientId);
        return patientResponseMapper.mapToDTO(patient);
    }

    @GetMapping("{professionalId}/patients/available") // Patients without Professional
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Page<PatientResponseDTO> getAvailablePatients(@PathVariable("professionalId") Long professionalId, Pageable pageable) {
        Page<PatientEntity> patientEntities = professionalService.getAvailablePatient(professionalId, pageable);
        return patientEntities.map(patientResponseMapper::mapToDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Page<ProfessionalDTO> listProfessionals(Pageable pageable) {
        Page<ProfessionalEntity> professionalEntities = professionalService.findAll(pageable);
        return professionalEntities.map(professionalMapper::mapToDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ProfessionalDTO> getProfessionalById(@PathVariable("id") Long id) {
        Optional<ProfessionalEntity> professional = professionalService.getProfessionalById(id);
        return professional.map(professionalEntity -> {
            ProfessionalDTO professionalDTO = professionalMapper.mapToDTO(professionalEntity);
            return new ResponseEntity<>(professionalDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nus/{nus}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ProfessionalResponseDTO> getProfessionalsById(@PathVariable("nus") String nus) {
        Optional<ProfessionalEntity> professional = professionalService.getProfessionalByNus(nus);
        return professional.map(professionalEntity -> {
            ProfessionalResponseDTO professionalDTO = professionalResponseMapper.mapToDTO(professionalEntity);
            return new ResponseEntity<>(professionalDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ProfessionalDTO> fullUpdateProfessional(@PathVariable("id") Long id, @RequestBody @Valid ProfessionalDTO professionalDTO) {
        if (!professionalService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        professionalDTO.setId(id);
        ProfessionalEntity professionalEntity = professionalMapper.mapFrom(professionalDTO);
        ProfessionalEntity savedProfessionalEntity = professionalService.save(professionalEntity);
        return new ResponseEntity<>(
                professionalMapper.mapToDTO(savedProfessionalEntity), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ProfessionalDTO> partialUpdateProfessional(@PathVariable("id") Long id, @RequestBody @Valid ProfessionalDTO professionalDTO) {
        if (!professionalService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProfessionalEntity professionalEntity = professionalMapper.mapFrom(professionalDTO);
        ProfessionalEntity savedProfessionalEntity = professionalService.partialUpdate(id, professionalEntity);
        return new ResponseEntity<>(
                professionalMapper.mapToDTO(savedProfessionalEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity deleteProfessional(@PathVariable("id") Long id) {
        if (!professionalService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        professionalService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


