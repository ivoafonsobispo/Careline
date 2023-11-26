package pt.ipleiria.careline.services.impl;

import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.repositories.PatientRepository;
import pt.ipleiria.careline.services.PatientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientEntity save(PatientEntity patientEntity) {
        return patientRepository.save(patientEntity);
    }

    @Override
    public Optional<PatientEntity> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public List<PatientEntity> findAll() {
        return StreamSupport.stream(patientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isExists(Long id) {
        return patientRepository.existsById(id);
    }

    @Override
    public PatientEntity partialUpdate(Long id, PatientEntity patientEntity) {
        patientEntity.setId(id);
        return patientRepository.findById(id).map(existingPatient -> {
            Optional.ofNullable(patientEntity.getName()).ifPresent(existingPatient::setName);
            Optional.ofNullable(patientEntity.getEmail()).ifPresent(existingPatient::setEmail);
            Optional.ofNullable(patientEntity.getPassword()).ifPresent(existingPatient::setPassword);
            Optional.ofNullable(patientEntity.getNus()).ifPresent(existingPatient::setNus);
            return patientRepository.save(existingPatient);
        }).orElseThrow(() -> new RuntimeException("Patient not found"));
    }
}
