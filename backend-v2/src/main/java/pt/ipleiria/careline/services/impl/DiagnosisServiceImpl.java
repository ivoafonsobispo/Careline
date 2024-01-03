package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.exceptions.DroneException;
import pt.ipleiria.careline.exceptions.PatientException;
import pt.ipleiria.careline.repositories.DiagnosisRepository;
import pt.ipleiria.careline.services.DiagnosisService;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.ProfessionalService;
import pt.ipleiria.careline.utils.DateConversionUtil;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final ProfessionalService professionalService;
    private final PatientService patientService;

    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository, ProfessionalService professionalService, PatientService patientService) {
        this.diagnosisRepository = diagnosisRepository;
        this.professionalService = professionalService;
        this.patientService = patientService;
    }

    // TODO: Unit Tests

    private static void diagnosisBelongsToPatient(Long patientId, Page<DiagnosisEntity> diagnosisEntities) {
        if (diagnosisEntities.get().anyMatch(diagnosis -> !diagnosis.getPatient().getId().equals(patientId))) {
            throw new DroneException("Diagnosis does not belong to patient");
        }
    }

    @Override
    public DiagnosisEntity save(Long patientId, Long professionalId, DiagnosisEntity diagnosisEntity) {
        Optional<PatientEntity> existingPatient = patientService.getPatientById(patientId);
        Optional<ProfessionalEntity> existingProfessional = professionalService.getProfessionalById(patientId);

        if (existingPatient.isPresent()) {
            diagnosisEntity.setPatient(existingPatient.get());
        } else {
            throw new PatientException();
        }

        if (existingProfessional.isPresent()) {
            diagnosisEntity.setProfessional(existingProfessional.get());
        } else {
            throw new PatientException();
        }

        return diagnosisRepository.save(diagnosisEntity);
    }

    @Override
    public Optional<DiagnosisEntity> getById(Long id) {
        return diagnosisRepository.findById(id);
    }

    @Override
    public List<DiagnosisEntity> findAll() {
        return StreamSupport.stream(diagnosisRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DiagnosisEntity> findAll(Pageable pageable) {
        return diagnosisRepository.findAll(pageable);
    }

    @Override
    public boolean isExists(Long id) {
        return diagnosisRepository.existsById(id);
    }

    @Override
    public DiagnosisEntity partialUpdate(Long id, DiagnosisEntity diagnosisEntity) {
        diagnosisEntity.setId(id);
        return diagnosisRepository.save(diagnosisEntity);
    }

    @Override
    public void delete(Long id) {
        diagnosisRepository.deleteById(id);
    }

    @Override
    public Optional<DiagnosisEntity> getPDFById(Long id) {
        return diagnosisRepository.findById(id);
    }

    @Override
    public Optional<DiagnosisEntity> getDiagnosisOfPatient(Long patientId, Long id) {
        Optional<DiagnosisEntity> diagnosisEntity = diagnosisRepository.findByIdOfPatient(patientId, id);
        if (diagnosisEntity.isEmpty()) {
            throw new DroneException();
        }
        if (!diagnosisEntity.get().getPatient().getId().equals(patientId)) {
            throw new DroneException("Diagnosis does not belong to patient");
        }

        return diagnosisEntity;
    }

    @Override
    public Page<DiagnosisEntity> findAllDiagnosisOfPatient(Long patientId, Pageable pageable) {
        Page<DiagnosisEntity> diagnosisEntities = diagnosisRepository.findAllByPatientId(patientId, pageable);
        diagnosisBelongsToPatient(patientId, diagnosisEntities);

        return diagnosisEntities;
    }

    @Override
    public Page<DiagnosisEntity> findAllLatest(Pageable pageable, Long patientId) {
        Page<DiagnosisEntity> diagnosisEntities = diagnosisRepository.findAllByPatientIdOrderByCreatedAtDesc(patientId, pageable);
        diagnosisBelongsToPatient(patientId, diagnosisEntities);

        return diagnosisEntities;
    }

    @Override
    public Page<DiagnosisEntity> findAllByDate(Pageable pageable, Long patientId, String date) {
        DateConversionUtil dateConversionUtil = new DateConversionUtil();
        Instant startDate = dateConversionUtil.convertStringToStartOfDayInstant(date);
        Instant endDate = dateConversionUtil.convertStringToEndOfDayInstant(date);

        Page<DiagnosisEntity> diagnosisEntities = diagnosisRepository.findAllByPatientIdAndCreatedAtBetweenOrderByCreatedAtDesc(pageable, patientId, startDate, endDate);
        diagnosisBelongsToPatient(patientId, diagnosisEntities);

        return diagnosisEntities;
    }

    @Override
    public Page<DiagnosisEntity> findAllLatestFromProfessional(Pageable pageable, Long professionalId) {
        return diagnosisRepository.findAllByProfessionalIdOrderByCreatedAtDesc(professionalId, pageable);
    }
}
