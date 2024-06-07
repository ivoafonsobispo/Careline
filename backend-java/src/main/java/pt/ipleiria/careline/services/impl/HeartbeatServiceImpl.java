package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Severity;
import pt.ipleiria.careline.exceptions.HeartbeatException;
import pt.ipleiria.careline.exceptions.PatientException;
import pt.ipleiria.careline.helpers.HeartbeatSeverity;
import pt.ipleiria.careline.repositories.HeartbeatRepository;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.utils.DateConversionUtil;
import pt.ipleiria.careline.validations.DataValidation;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HeartbeatServiceImpl implements HeartbeatService {
    private final HeartbeatRepository heartbeatRepository;
    private final PatientService patientService;

    public HeartbeatServiceImpl(HeartbeatRepository heartbeatRepository, PatientService patientService) {
        this.heartbeatRepository = heartbeatRepository;
        this.patientService = patientService;
    }

    private static void heartbeatBelongsToPatient(Long patientId, Page<HeartbeatEntity> heartbeatEntities) {
        if (heartbeatEntities.get().anyMatch(heartbeat -> !heartbeat.getPatient().getId().equals(patientId))) {
            throw new HeartbeatException("Heartbeat does not belong to patient");
        }
    }

    @Override
    public HeartbeatEntity create(Long patientId, HeartbeatEntity heartbeatEntity) {
        if (!DataValidation.isHeartbeatValid(heartbeatEntity.getHeartbeat())) {
            throw new HeartbeatException();
        }

        Optional<PatientEntity> existingPatient = patientService.getPatientById(patientId);
        if (existingPatient.isEmpty()) {
            throw new PatientException();
        }

        heartbeatEntity.setPatient(existingPatient.get());
        HeartbeatSeverity heartbeatSeverity = new HeartbeatSeverity();
        Severity severity = heartbeatSeverity.getSeverityCategory(heartbeatEntity.getHeartbeat());
        heartbeatEntity.setSeverity(severity);

        return heartbeatRepository.save(heartbeatEntity);
    }

    @Override
    public List<HeartbeatEntity> findAll() {
        return StreamSupport.stream(heartbeatRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<HeartbeatEntity> findAll(Pageable pageable, Long patientId) {
        Page<HeartbeatEntity> heartbeatEntities = heartbeatRepository.findAllByPatientId(pageable, patientId);
        heartbeatBelongsToPatient(patientId, heartbeatEntities);

        return heartbeatEntities;
    }

    @Override
    public Page<HeartbeatEntity> findAllLatest(Pageable pageable, Long patientId) {
        return heartbeatRepository.findAllByPatientIdOrderByCreatedAtDesc(pageable, patientId);
    }

    @Override
    public Optional<HeartbeatEntity> getById(Long id) {
        return heartbeatRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return heartbeatRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        heartbeatRepository.deleteById(id);
    }

    @Override
    public Page<HeartbeatEntity> findAllByDate(Pageable pageable, Long patientId, String date) {
        DateConversionUtil dateConversionUtil = new DateConversionUtil();
        Instant startDate = dateConversionUtil.convertStringToStartOfDayInstant(date);
        Instant endDate = dateConversionUtil.convertStringToEndOfDayInstant(date);

        Page<HeartbeatEntity> heartbeatEntities = heartbeatRepository.findAllByPatientIdAndCreatedAtBetweenOrderByCreatedAtDesc(
                pageable, patientId, startDate, endDate);
        heartbeatBelongsToPatient(patientId, heartbeatEntities);

        return heartbeatEntities;
    }
}
