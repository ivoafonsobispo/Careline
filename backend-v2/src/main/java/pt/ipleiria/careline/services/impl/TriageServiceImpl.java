package pt.ipleiria.careline.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.TriageEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Severity;
import pt.ipleiria.careline.domain.enums.Status;
import pt.ipleiria.careline.exceptions.PatientException;
import pt.ipleiria.careline.helpers.HeartbeatSeverity;
import pt.ipleiria.careline.helpers.TemperatureSeverity;
import pt.ipleiria.careline.repositories.TriageRepository;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.TriageService;
import pt.ipleiria.careline.utils.DateConversionUtil;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TriageServiceImpl implements TriageService {
    private final TriageRepository triageRepository;
    private final PatientService patientService;

    public TriageServiceImpl(TriageRepository triageRepository, PatientService patientService) {
        this.triageRepository = triageRepository;
        this.patientService = patientService;
    }

    @Override
    public TriageEntity save(TriageEntity triageEntity, Long patientId) {
        // Set Patient
        Optional<PatientEntity> patient = patientService.getPatientById(patientId);
        if (patient.isEmpty()) {
            log.atError().log("Patient does not exist - {}", patientId);
            throw new PatientException();
        }

        triageEntity.setPatient(patient.get());

        // Set Severity
        HeartbeatSeverity heartbeatSeverity = new HeartbeatSeverity();
        TemperatureSeverity temperatureSeverity = new TemperatureSeverity();

        Severity heartbeatSeverityCategory = heartbeatSeverity.getSeverityCategory(triageEntity.getHeartbeat());
        Severity temperatureSeverityCategory = temperatureSeverity.getSeverityCategory(triageEntity.getTemperature());

        if (heartbeatSeverityCategory == Severity.CRITICAL || temperatureSeverityCategory == Severity.CRITICAL) {
            triageEntity.setSeverity(Severity.CRITICAL);
        } else if (heartbeatSeverityCategory == Severity.MEDIUM || temperatureSeverityCategory == Severity.MEDIUM) {
            triageEntity.setSeverity(Severity.MEDIUM);
        } else {
            triageEntity.setSeverity(Severity.GOOD);
        }

        // Set Status
        triageEntity.setStatus(Status.UNREVIEWED);
        triageEntity.setReviewDate(Instant.EPOCH);

        log.atInfo().log("Saving triage - {}", triageEntity);
        return triageRepository.save(triageEntity);
    }

    @Override
    public Optional<TriageEntity> getTriageById(Long id) {
        log.atInfo().log("Getting triage by id - {}", id);
        return triageRepository.findById(id);
    }

    @Override
    public Page<TriageEntity> getTriagesByPatient(Pageable pageable, PatientEntity patient) {
        log.atInfo().log("Getting triages by patient - {}", patient);
        return triageRepository.getTriagesByPatient(pageable, patient);
    }

    @Override
    public Optional<TriageEntity> getTriageByPatient(PatientEntity patient, Long triageID) {
        log.atInfo().log("Getting triage by patient - {}", patient);
        return triageRepository.getTriageByPatient(patient, triageID);
    }

    @Override
    public Optional<TriageEntity> findLastParientTriage(PatientEntity patient) {
        log.atInfo().log("Getting last triage by patient - {}", patient);
        return triageRepository.findLastParientTriage(patient);
    }

    @Override
    public List<TriageEntity> findAll() {
        log.atInfo().log("Getting all triages");
        return triageRepository.findAll();
    }

    @Override
    public Page<TriageEntity> findAll(Pageable pageable) {
        log.atInfo().log("Getting all triages");
        return triageRepository.findAll(pageable);
    }

    @Override
    public Optional<TriageEntity> findLastTriage() {
        log.atInfo().log("Getting last triage");
        return triageRepository.findLastTriage();
    }

    @Override
    public boolean isExists(Long id) {
        log.atInfo().log("Checking if triage exists - {}", id);
        return triageRepository.existsById(id);
    }

    @Override
    public TriageEntity partialUpdate(Long id, TriageEntity triageEntity) {
        triageEntity.setId(id);
        log.info("Partial update Triage - {}", triageEntity);
        return triageRepository.findById(id).map(existingTriage -> {
            Optional.ofNullable(triageEntity.getHeartbeat()).ifPresent(existingTriage::setHeartbeat);
            Optional.ofNullable(triageEntity.getTemperature()).ifPresent(existingTriage::setTemperature);
            Optional.ofNullable(triageEntity.getSymptoms()).ifPresent(existingTriage::setSymptoms);
            Optional.ofNullable(triageEntity.getPatient()).ifPresent(existingTriage::setPatient);
            Optional.ofNullable(triageEntity.getSeverity()).ifPresent(existingTriage::setSeverity);
            Optional.ofNullable(triageEntity.getStatus()).ifPresent(existingTriage::setStatus);
            Optional.ofNullable(triageEntity.getReviewDate()).ifPresent(existingTriage::setReviewDate);
            return triageRepository.save(existingTriage);
        }).orElseThrow(() -> new RuntimeException("Triage not found"));
    }

    @Override
    public void delete(Long id) {
        log.info("Delete Triage - {}", id);
        Optional<TriageEntity> opt = triageRepository.findById(id);
        opt.ifPresent(triageRepository::delete);
    }

    @Override
    public Page<TriageEntity> findAllByDate(Pageable pageable, String date) {
        DateConversionUtil dateConversionUtil = new DateConversionUtil();
        Instant startDate = dateConversionUtil.convertStringToStartOfDayInstant(date);
        Instant endDate = dateConversionUtil.convertStringToEndOfDayInstant(date);

        log.atInfo().log("Getting all triages by date - {}", date);

        return triageRepository.findAllByCreatedAtBetweenOrderByCreatedAtDesc(pageable, startDate, endDate);
    }

    @Override
    public Page<TriageEntity> findAllLatest(Pageable pageable) {
        log.atInfo().log("Getting all latest triages");
        return triageRepository.findAllOrderByCreatedAtDesc(pageable);
    }

    @Override
    public TriageEntity setTriageReviewed(Long patientId, Long triageId) {
        Optional<TriageEntity> triage = triageRepository.findById(triageId);
        if (triage.isEmpty()) {
            log.atError().log("Triage does not exist - {}", triageId);
            throw new RuntimeException("Triage not found");
        }

        triage.get().setStatus(Status.REVIEWED);
        triage.get().setReviewDate(Instant.now());
        log.atInfo().log("Changed Triage status to REVIEWED - {}", triageId);
        return partialUpdate(triageId, triage.get());
    }

    @Override
    public Page<TriageEntity> findAllUnreviewed(Pageable pageable) {
        log.atInfo().log("Getting all unreviewed triages");
        return triageRepository.findAllByStatusOrderByCreatedAtDesc(pageable, Status.UNREVIEWED);
    }

    @Override
    public Page<TriageEntity> findAllOfPatient(Pageable pageable, Long patientId) {
        log.atInfo().log("Getting all triages of patient - {}", patientId);
        return triageRepository.findAllByPatientIdOrderByCreatedAtDesc(pageable, patientId);
    }
}
