package pt.ipleiria.careline.controllers;

import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.DiagnosisDTO;
import pt.ipleiria.careline.domain.dto.responses.DiagnosisResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.ProfessionalResponseDTO;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.DiagnosisService;
import pt.ipleiria.careline.utils.PdfGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Optional;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class DiagnosisController {
    private final DiagnosisService diagnosisService;
    private final Mapper<DiagnosisEntity, DiagnosisDTO> diagnosisMapper;
    private final Mapper<DiagnosisEntity, DiagnosisResponseDTO> diagnosisResponseMapper;
    private final Mapper<PatientEntity, PatientResponseDTO> patientResponseDTOMapper;
    private final Mapper<ProfessionalEntity, ProfessionalResponseDTO> professionalResponseDTOMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public DiagnosisController(DiagnosisService diagnosisService,
                               Mapper<DiagnosisEntity, DiagnosisDTO> diagnosisMapper, Mapper<DiagnosisEntity, DiagnosisResponseDTO> diagnosisResponseMapper, Mapper<PatientEntity, PatientResponseDTO> patientResponseDTOMapper, Mapper<ProfessionalEntity, ProfessionalResponseDTO> professionalResponseDTOMapper, SimpMessagingTemplate messagingTemplate) {
        this.diagnosisService = diagnosisService;
        this.diagnosisMapper = diagnosisMapper;
        this.diagnosisResponseMapper = diagnosisResponseMapper;
        this.patientResponseDTOMapper = patientResponseDTOMapper;
        this.professionalResponseDTOMapper = professionalResponseDTOMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<DiagnosisResponseDTO> create(@PathVariable("professionalId") Long professionalId,
                                                       @PathVariable("patientId") Long patientId, @RequestBody @Valid DiagnosisDTO diagnosisDTO) {
        // Log roles
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User roles: " + authentication.getAuthorities());

        DiagnosisEntity diagnosisEntity = diagnosisMapper.mapFrom(diagnosisDTO);
        DiagnosisEntity savedDiagnosticEntity = diagnosisService.save(patientId, professionalId, diagnosisEntity);
        DiagnosisResponseDTO responseDTO = new DiagnosisResponseDTO(savedDiagnosticEntity.getId(), patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient()), professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional()), savedDiagnosticEntity.getDiagnosis(), savedDiagnosticEntity.getMedications(), Instant.now());

        messagingTemplate.convertAndSend("/topic/diagnosis", responseDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
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
    @PreAuthorize("hasRole('PROFESSIONAL')")
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
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (!diagnosisService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        diagnosisService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET - Related to Professional Diagnosis
    @GetMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Page<DiagnosisResponseDTO> listDiagnostics(@PathVariable("professionalId") Long professionalId, @PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<DiagnosisEntity> diagnosisEntities = diagnosisService.findAll(professionalId, patientId, pageable);
        return diagnosisEntities.map(diagnosisEntity -> {
            PatientResponseDTO patientDTO = patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient());
            ProfessionalResponseDTO professionalDTO = professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional());
            return new DiagnosisResponseDTO(
                    diagnosisEntity.getId(),
                    patientDTO,
                    professionalDTO,
                    diagnosisEntity.getDiagnosis(),
                    diagnosisEntity.getMedications(),
                    diagnosisEntity.getCreatedAt()
            );
        });
    }

    @GetMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<DiagnosisResponseDTO> getByIdProfessional(@PathVariable("id") Long id) {
        Optional<DiagnosisEntity> diagnosis = diagnosisService.getById(id);
        return diagnosis.map(diagnosisEntity -> {
            DiagnosisResponseDTO diagnosisDTO = new DiagnosisResponseDTO(diagnosisEntity.getId(), patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient()), professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional()), diagnosisEntity.getDiagnosis(), diagnosisEntity.getMedications(), diagnosisEntity.getCreatedAt());
            return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/date/{date}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Page<DiagnosisResponseDTO> professionalListDiagnosisByDate(@PathVariable("professionalId") Long professionalId, @PathVariable("patientId") Long patientId, @PathVariable("date") String date, Pageable pageable) {
        Page<DiagnosisEntity> diagnosis = diagnosisService.findAllByDate(pageable, patientId, date);
        return diagnosis.map(diagnosisEntity -> new DiagnosisResponseDTO(diagnosisEntity.getId(), patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient()), professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional()), diagnosisEntity.getDiagnosis(), diagnosisEntity.getMedications(), diagnosisEntity.getCreatedAt()));
    }

    @GetMapping("/professionals/{professionalId}/patients/{patientId}/diagnosis/{id}/pdf")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public void getPDFById(@PathVariable("patientId") Long patientId, @PathVariable("id") Long id, HttpServletResponse response) throws DocumentException, IOException {
        Optional<DiagnosisEntity> diagnosisEntity = diagnosisService.getDiagnosisOfPatient(patientId, id);
        PdfGenerator generator = new PdfGenerator();
        byte[] pdfContent = generator.generateDiagnosisPDF(diagnosisEntity.get());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=diagnosis_report.pdf");
        response.setContentLength(pdfContent.length);

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(pdfContent);
            outputStream.flush();
        }
    }

    @GetMapping("professionals/{professionalId}/diagnosis/latest")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Page<DiagnosisResponseDTO> listLatestDiagnosisFromProfessional(@PathVariable("professionalId") Long professionalId, Pageable pageable) {
        Page<DiagnosisEntity> diagnosisEntities = diagnosisService.findAllLatestFromProfessional(pageable, professionalId);
        return diagnosisEntities.map(diagnosisEntity -> {
            PatientResponseDTO patientDTO = patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient());
            ProfessionalResponseDTO professionalDTO = professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional());
            return new DiagnosisResponseDTO(
                    diagnosisEntity.getId(),
                    patientDTO,
                    professionalDTO,
                    diagnosisEntity.getDiagnosis(),
                    diagnosisEntity.getMedications(),
                    diagnosisEntity.getCreatedAt()
            );
        });
    }

    @GetMapping("/professionals/{professionalId}/diagnosis/date/{date}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Page<DiagnosisResponseDTO> professionalListDiagnosisByDate(@PathVariable("professionalId") Long professionalId, @PathVariable("date") String date, Pageable pageable) {
        Page<DiagnosisEntity> diagnosis = diagnosisService.findAllByDateFromProfessional(pageable, professionalId, date);
        return diagnosis.map(diagnosisEntity -> new DiagnosisResponseDTO(diagnosisEntity.getId(), patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient()), professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional()), diagnosisEntity.getDiagnosis(), diagnosisEntity.getMedications(), diagnosisEntity.getCreatedAt()));
    }

    @GetMapping("professionals/{professionalId}/patients/{patientId}/diagnosis/latest")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public Page<DiagnosisResponseDTO> listLatestProfessional(@PathVariable(
            "patientId") Long patientId, Pageable pageable) {
        Page<DiagnosisEntity> diagnosisEntities =
                diagnosisService.findAllLatest(pageable,
                        patientId);
        return diagnosisEntities.map(diagnosisEntity -> {
            PatientResponseDTO patientDTO = patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient());
            ProfessionalResponseDTO professionalDTO = professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional());
            return new DiagnosisResponseDTO(
                    diagnosisEntity.getId(),
                    patientDTO,
                    professionalDTO,
                    diagnosisEntity.getDiagnosis(),
                    diagnosisEntity.getMedications(),
                    diagnosisEntity.getCreatedAt()
            );
        });
    }

    // GET - Related to Patient Diagnosis
    @GetMapping("/patients/{patientId}/diagnosis/date/{date}")
    @PreAuthorize("hasRole('PATIENT')")
    public Page<DiagnosisResponseDTO> patientListDiagnosisByDate(@PathVariable("patientId") Long patientId, @PathVariable("date") String date, Pageable pageable) {
        Page<DiagnosisEntity> diagnosis = diagnosisService.findAllByDate(pageable, patientId, date);
        return diagnosis.map(diagnosisEntity -> new DiagnosisResponseDTO(diagnosisEntity.getId(), patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient()), professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional()), diagnosisEntity.getDiagnosis(), diagnosisEntity.getMedications(), diagnosisEntity.getCreatedAt()));
    }

    @GetMapping("/patients/{patientId}/diagnosis/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<DiagnosisResponseDTO> getByIdPatient(@PathVariable("id") Long id,
                                                               @PathVariable("patientId") Long patientId) {
        Optional<DiagnosisEntity> diagnosis = diagnosisService.getDiagnosisOfPatient(patientId, id);
        return diagnosis.map(diagnosisEntity -> {
            DiagnosisResponseDTO diagnosisDTO = new DiagnosisResponseDTO(diagnosisEntity.getId(), patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient()), professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional()), diagnosisEntity.getDiagnosis(), diagnosisEntity.getMedications(), diagnosisEntity.getCreatedAt());
            return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patients/{patientId}/diagnosis")
    @PreAuthorize("hasRole('PATIENT')")
    public Page<DiagnosisResponseDTO> listDiagnosticsPatient(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<DiagnosisEntity> diagnosisEntities = diagnosisService.findAllDiagnosisOfPatient(patientId, pageable);
        return diagnosisEntities.map(diagnosisResponseMapper::mapToDTO);
    }

    @GetMapping("/patients/{patientId}/diagnosis/{id}/pdf")
    @PreAuthorize("hasRole('PATIENT')")
    public void getPDFByIdPatient(@PathVariable("id") Long id, @PathVariable("patientId") Long patientId, HttpServletResponse response) throws DocumentException, IOException {
        Optional<DiagnosisEntity> diagnosisEntity = diagnosisService.getDiagnosisOfPatient(patientId, id);
        PdfGenerator generator = new PdfGenerator();
        byte[] pdfContent = generator.generateDiagnosisPDF(diagnosisEntity.get());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=diagnosis_report.pdf");
        response.setContentLength(pdfContent.length);

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(pdfContent);
            outputStream.flush();
        }
    }

    @GetMapping("patients/{patientId}/diagnosis/latest")
    @PreAuthorize("hasRole('PATIENT')")
    public Page<DiagnosisResponseDTO> listLatest(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<DiagnosisEntity> diagnosisEntities =
                diagnosisService.findAllLatest(pageable,
                        patientId);
        return diagnosisEntities.map(diagnosisEntity -> {
            PatientResponseDTO patientDTO = patientResponseDTOMapper.mapToDTO(diagnosisEntity.getPatient());
            ProfessionalResponseDTO professionalDTO = professionalResponseDTOMapper.mapToDTO(diagnosisEntity.getProfessional());
            return new DiagnosisResponseDTO(
                    diagnosisEntity.getId(),
                    patientDTO,
                    professionalDTO,
                    diagnosisEntity.getDiagnosis(),
                    diagnosisEntity.getMedications(),
                    diagnosisEntity.getCreatedAt()
            );
        });
    }
}
