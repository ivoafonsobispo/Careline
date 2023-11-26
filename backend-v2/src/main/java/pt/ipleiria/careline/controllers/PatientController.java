package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.PatientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return new ResponseEntity<>(patientMapper.mapToDTO(savedPatientEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public List<PatientDTO> listPatients() {
        List<PatientEntity> patients = patientService.findAll();
        return patients.stream().map(patientMapper::mapToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable("id") Long id) {
        Optional<PatientEntity> patient = patientService.getPatientById(id);
        return patient.map(patientEntity -> {
            PatientDTO patientDTO = patientMapper.mapToDTO(patientEntity);
            return new ResponseEntity<>(patientDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}


