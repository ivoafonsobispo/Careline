package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import java.util.List;
import java.util.Optional;

public interface TriageService  {

    TriageEntity save(TriageEntity triageEntity);

    Optional<TriageEntity> getTriageById(Long id);

    Page<TriageEntity> getTriageByPatient(Pageable pageable, PatientEntity patient);

    List<TriageEntity> findAll();

    Page<TriageEntity> findAll(Pageable pageable);

    Optional<TriageEntity> findLastTriage();

    boolean isExists(Long id);

    TriageEntity partialUpdate(Long id, TriageEntity triageEntity);

    void delete(Long id);

}
