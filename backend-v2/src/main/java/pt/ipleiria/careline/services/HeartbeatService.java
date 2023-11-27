package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;

import java.util.List;
import java.util.Optional;

public interface HeartbeatService {
    HeartbeatEntity createHeartbeat(Long patientId, HeartbeatEntity heartbeatEntity);

    List<HeartbeatEntity> findAll();

    Page<HeartbeatEntity> findAll(Pageable pageable, Long patientId);

    Optional<HeartbeatEntity> getHeartbeatById(Long id);

    boolean isExists(Long id);

    void delete(Long id);
}
