package pt.ipleiria.careline.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long>,
        PagingAndSortingRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByNus(String nus);

    Optional<PatientEntity> findByEmail(String email);
    Page<PatientEntity> findByProfessionalsId(Long id, Pageable pageable);
    Page<PatientEntity> findByProfessionalsIdIsNull(Pageable pageable);
}
