package pt.ipleiria.careline.services;

import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;

import java.util.Optional;

public interface HeartbeatService extends HealthDataService<HeartbeatEntity> {

    Optional<HeartbeatEntity> getHeartbeatById(Long id);

}
