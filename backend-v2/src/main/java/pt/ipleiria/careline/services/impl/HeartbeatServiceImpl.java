package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.repositories.HeartbeatRepository;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.PatientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HeartbeatServiceImpl implements HeartbeatService {

    private HeartbeatRepository heartbeatRepository;
    private PatientService patientService;

    public HeartbeatServiceImpl(HeartbeatRepository heartbeatRepository, PatientService patientService) {
        this.heartbeatRepository = heartbeatRepository;
        this.patientService = patientService;
    }

    @Override
    public HeartbeatEntity createHeartbeat(Long patientId, HeartbeatEntity heartbeatEntity) {
        Optional<PatientEntity> existingPatient = patientService.getPatientById(patientId);

        if (existingPatient.isPresent()) {
            heartbeatEntity.setPatient(existingPatient.get());
        } else {
            PatientEntity newPatient = heartbeatEntity.getPatient();
            patientService.save(newPatient);
            heartbeatEntity.setPatient(newPatient);
        }

        return heartbeatRepository.save(heartbeatEntity);
    }

    @Override
    public List<HeartbeatEntity> findAll() {
        return StreamSupport.stream(heartbeatRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<HeartbeatEntity> findAll(Pageable pageable) {
        return heartbeatRepository.findAll(pageable);
    }

    @Override
    public Optional<HeartbeatEntity> getHeartbeatById(Long id) {
        return heartbeatRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return heartbeatRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        heartbeatRepository.deleteById(id);
    }
}
