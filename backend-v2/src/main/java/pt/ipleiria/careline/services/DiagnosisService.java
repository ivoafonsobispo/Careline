package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;

import java.util.List;
import java.util.Optional;

public interface DiagnosisService {
    DiagnosisEntity save(Long patientId, Long professionalId ,DiagnosisEntity diagnosisEntity);

    Optional<DiagnosisEntity> getById(Long id);

    List<DiagnosisEntity> findAll();

    Page<DiagnosisEntity> findAll(Pageable pageable);

    boolean isExists(Long id);

    DiagnosisEntity partialUpdate(Long id, DiagnosisEntity diagnosisEntity);

    void delete(Long id);

    Optional<DiagnosisEntity> getPDFById(Long id);

    Optional<DiagnosisEntity> getDiagnosisOfPatient(Long patientId,Long id);

    Page<DiagnosisEntity> findAllDiagnosisOfPatient(Long patientId,Pageable pageable);

    Page<DiagnosisEntity> findAllLatest(Pageable pageable, Long patientId);
}
