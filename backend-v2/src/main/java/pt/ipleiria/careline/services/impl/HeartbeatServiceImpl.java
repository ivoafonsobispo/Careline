package pt.ipleiria.careline.services.impl;

import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;
import pt.ipleiria.careline.repositories.HeartbeatRepository;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.PatientService;

@Service
public class HeartbeatServiceImpl implements HeartbeatService {

    private HeartbeatRepository heartbeatRepository;
    private PatientService patientService;

    public HeartbeatServiceImpl(HeartbeatRepository heartbeatRepository) {
        this.heartbeatRepository = heartbeatRepository;
    }

    @Override
    public HeartbeatEntity createHeartbeat(Integer patientId, HeartbeatEntity heartbeatEntity) {
        return heartbeatRepository.save(heartbeatEntity);
    }
}
