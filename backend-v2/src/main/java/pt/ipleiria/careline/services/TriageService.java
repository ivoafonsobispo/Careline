package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;

import java.util.List;
import java.util.Optional;

public interface TriageService  {

    TriageEntity save(TriageEntity triageEntity);

    Optional<TriageEntity> getTriageById(Long id);

    Page<TriageEntity> getTriagesByPatient(Pageable pageable, PatientEntity patient);

    Optional<TriageEntity> getTriageByPatient(PatientEntity patient, Long triageId);

    Optional<TriageEntity> findLastParientTriage(PatientEntity patient);

    List<TriageEntity> findAll();

    Page<TriageEntity> findAll(Pageable pageable);

    Optional<TriageEntity> findLastTriage();

    boolean isExists(Long id);

    TriageEntity partialUpdate(Long id, TriageEntity triageEntity);

    void delete(Long id);

}
