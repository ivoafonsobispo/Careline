package pt.ipleiria.careline.services.impl;

import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.repositories.PatientRepository;
import pt.ipleiria.careline.services.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientEntity createPatient(PatientEntity patientEntity) {
        return patientRepository.save(patientEntity);
    }
}
