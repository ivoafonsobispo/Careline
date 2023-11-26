package pt.ipleiria.careline.services;

import pt.ipleiria.careline.domain.entities.users.PatientEntity;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    PatientEntity save(PatientEntity patientEntity);

    Optional<PatientEntity> getPatientById(Long id);

    List<PatientEntity> findAll();

    boolean isExists(Long id);

    PatientEntity partialUpdate(Long id, PatientEntity patientEntity);
}
