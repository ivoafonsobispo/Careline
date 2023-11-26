package pt.ipleiria.careline.services;

import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;

public interface HeartbeatService {
    HeartbeatEntity createHeartbeat(Integer patientId, HeartbeatEntity heartbeatEntity);
}
