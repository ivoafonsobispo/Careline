package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import java.util.List;
import java.util.Optional;

public interface ProfessionalService {
    ProfessionalEntity save(ProfessionalEntity professionalEntity);

    Optional<ProfessionalEntity> getProfessionalById(Long id);

    Optional<ProfessionalEntity> getProfessionalByNus(String nus);

    List<ProfessionalEntity> findAll();

    Page<ProfessionalEntity> findAll(Pageable pageable);

    boolean isExists(Long id);

    ProfessionalEntity partialUpdate(Long id, ProfessionalEntity patientEntity);

    void delete(Long id);
}
