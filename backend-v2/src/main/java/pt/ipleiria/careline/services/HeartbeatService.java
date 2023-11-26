package pt.ipleiria.careline.services;

import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;

import java.util.List;
import java.util.Optional;

public interface HeartbeatService {
    HeartbeatEntity createHeartbeat(Integer patientId, HeartbeatEntity heartbeatEntity);

    List<HeartbeatEntity> findAll();

    Optional<HeartbeatEntity> getHeartbeatById(Long id);
}
