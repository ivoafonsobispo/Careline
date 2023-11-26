package pt.ipleiria.careline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;

@Repository
public interface HeartbeatRepository extends JpaRepository<HeartbeatEntity, Long> {
}
