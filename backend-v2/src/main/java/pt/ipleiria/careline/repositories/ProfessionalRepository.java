package pt.ipleiria.careline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long>,
        PagingAndSortingRepository<ProfessionalEntity, Long> {
    Optional<ProfessionalEntity> findByNus(String nus);
    Optional<ProfessionalEntity> findByEmail(String email);
}
