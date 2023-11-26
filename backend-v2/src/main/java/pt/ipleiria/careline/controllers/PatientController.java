package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.PatientService;

@RequestMapping("/api/patients")
@RestController
public class PatientController {

    private final PatientService patientService;
    private final Mapper<PatientEntity, PatientDTO> patientMapper;

    public PatientController(PatientService patientService, Mapper<PatientEntity, PatientDTO> patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @PostMapping
    public ResponseEntity<PatientDTO> create(@RequestBody @Valid PatientDTO patientDTO) {
        PatientEntity patientEntity = patientMapper.mapFrom(patientDTO);
        PatientEntity savedPatientEntity = patientService.createPatient(patientEntity);
        return ResponseEntity.ok(patientMapper.mapToDTO(savedPatientEntity));
    }

}
