package pt.ipleiria.careline.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TriageRepository extends JpaRepository<TriageEntity, Long>,
        PagingAndSortingRepository<TriageEntity, Long> {
    //get triage by id
    Optional<TriageEntity> findById(Long triageId);

    List<TriageEntity> findAllById( Long trisgeId);

    //get all triage data units at some time
    Page<TriageEntity> findAllByCreatedAt(Pageable pageable, Instant instant);
}
