package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    PatientEntity save(PatientEntity patientEntity);

    Optional<PatientEntity> getPatientById(Long id);

    Optional<PatientEntity> getPatientByNus(String nus);

    List<PatientEntity> findAll();

    Page<PatientEntity> findAll(Pageable pageable);

    boolean isExists(Long id);

    PatientEntity partialUpdate(Long id, PatientEntity patientEntity);
    @Transactional
    void setProfessionalToPatient(ProfessionalEntity professional, PatientEntity patient);
    void delete(Long id);
}
