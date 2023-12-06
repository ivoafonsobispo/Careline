package pt.ipleiria.careline.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;

@Repository
public interface DiagnosisRepository extends JpaRepository<DiagnosisEntity, Long>,
        PagingAndSortingRepository<DiagnosisEntity, Long> {
}
