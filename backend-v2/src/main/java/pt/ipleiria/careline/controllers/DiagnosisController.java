package pt.ipleiria.careline.controllers;

import com.lowagie.text.DocumentException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.DiagnosisDTO;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.DiagnosisService;
import pt.ipleiria.careline.utils.PdfGenerator;

import java.io.IOException;
import java.util.Optional;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class DiagnosisController {
    private final DiagnosisService diagnosisService;
    private Mapper<DiagnosisEntity, DiagnosisDTO> diagnosisMapper;

    public DiagnosisController(DiagnosisService diagnosisService, Mapper<DiagnosisEntity, DiagnosisDTO> diagnosisMapper) {
        this.diagnosisService = diagnosisService;
        this.diagnosisMapper = diagnosisMapper;
    }

    @PostMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis")
    public ResponseEntity<DiagnosisDTO> create(@PathVariable("professionalId") Long professionalId, @PathVariable("patientId") Long patientId, @RequestBody @Valid DiagnosisDTO diagnosisDTO) {
        DiagnosisEntity diagnosisEntity = diagnosisMapper.mapFrom(diagnosisDTO);
        DiagnosisEntity savedDiagnosticEntity = diagnosisService.save(patientId, professionalId, diagnosisEntity);
        return new ResponseEntity<>(diagnosisMapper.mapToDTO(savedDiagnosticEntity), HttpStatus.CREATED);
    }

    @GetMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis")
    public Page<DiagnosisDTO> listDiagnostics(Pageable pageable) {
        Page<DiagnosisEntity> diagnosisEntities = diagnosisService.findAll(pageable);
        return diagnosisEntities.map(diagnosisMapper::mapToDTO);
    }

    @GetMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/{id}")
    public ResponseEntity<DiagnosisDTO> getByIdProfessional(@PathVariable("id") Long id) {
        Optional<DiagnosisEntity> diagnosis = diagnosisService.getById(id);
        return diagnosis.map(diagnosisEntity -> {
            DiagnosisDTO diagnosisDTO = diagnosisMapper.mapToDTO(diagnosisEntity);
            return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patients/{patientId}/diagnosis/{id}")
    public ResponseEntity<DiagnosisDTO> getByIdPatient(@PathVariable("id") Long id, @PathVariable("patientId") Long patientId) {
        Optional<DiagnosisEntity> diagnosis = diagnosisService.getDiagnosisOfPatient(patientId, id);
        return diagnosis.map(diagnosisEntity -> {
            DiagnosisDTO diagnosisDTO = diagnosisMapper.mapToDTO(diagnosisEntity);
            return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patients/{patientId}/diagnosis")
    public Page<DiagnosisDTO> listDiagnosticsPatient(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<DiagnosisEntity> diagnosisEntities = diagnosisService.findAllDiagnosisOfPatient(patientId, pageable);
        return diagnosisEntities.map(diagnosisMapper::mapToDTO);
    }

    @GetMapping("/patients/{patientId}/diagnosis/{id}/pdf")
    public void getPDFByIdPatient(@PathVariable("id") Long id, @PathVariable("patientId") Long patientId) throws DocumentException, IOException {
        Optional<DiagnosisEntity> diagnosisEntity = diagnosisService.getDiagnosisOfPatient(patientId, id);
        if (diagnosisEntity.isEmpty()) {
            throw new IllegalArgumentException("Diagnosis not found");
        }
        PdfGenerator generator = new PdfGenerator();
        generator.generateDiagnosisPDF(diagnosisEntity.get());
    }

    @GetMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/{id}/pdf")
    public void getPDFById(@PathVariable("id") Long id) throws DocumentException, IOException {
        Optional<DiagnosisEntity> diagnosisEntity = diagnosisService.getById(id);
        if (diagnosisEntity.isEmpty()) {
            throw new IllegalArgumentException("Diagnosis not found");
        }
        PdfGenerator generator = new PdfGenerator();
        generator.generateDiagnosisPDF(diagnosisEntity.get());
    }

    @PutMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/{id}")
    public ResponseEntity<DiagnosisDTO> fullUpdate(@PathVariable("professionalId") Long professionalId, @PathVariable("patientId") Long patientId, @PathVariable("id") Long diagnosisId, @RequestBody @Valid DiagnosisDTO diagnosisDTO) {
        if (!diagnosisService.isExists(diagnosisId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        diagnosisDTO.setId(diagnosisId);
        DiagnosisEntity diagnosisEntity = diagnosisMapper.mapFrom(diagnosisDTO);
        DiagnosisEntity savedDiagnosisEntity = diagnosisService.save(patientId, professionalId, diagnosisEntity);
        return new ResponseEntity<>(
                diagnosisMapper.mapToDTO(savedDiagnosisEntity), HttpStatus.OK);
    }

    @PatchMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/{id}")
    public ResponseEntity<DiagnosisDTO> partialUpdate(@PathVariable("id") Long id, @RequestBody @Valid DiagnosisDTO diagnosisDTO) {
        if (!diagnosisService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DiagnosisEntity diagnosisEntity = diagnosisMapper.mapFrom(diagnosisDTO);
        DiagnosisEntity savedDiagnosisEntity = diagnosisService.partialUpdate(id, diagnosisEntity);
        return new ResponseEntity<>(
                diagnosisMapper.mapToDTO(savedDiagnosisEntity), HttpStatus.OK);
    }

    @DeleteMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (!diagnosisService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        diagnosisService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
