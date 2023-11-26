package pt.ipleiria.careline.services;

import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;

import java.util.List;

public interface HeartbeatService {
    HeartbeatEntity createHeartbeat(Integer patientId, HeartbeatEntity heartbeatEntity);

    List<HeartbeatEntity> findAll();
}
