package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.helpers.DataValidation;
import pt.ipleiria.careline.repositories.DiagnosisRepository;
import pt.ipleiria.careline.services.DiagnosisService;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.ProfessionalService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    private DiagnosisRepository diagnosisRepository;
    private ProfessionalService professionalService;
    private PatientService patientService;

    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository, ProfessionalService professionalService, PatientService patientService) {
        this.diagnosisRepository = diagnosisRepository;
        this.professionalService = professionalService;
        this.patientService = patientService;
    }

    // TODO: Missing Validations & Unit Tests

    @Override
    public DiagnosisEntity save(Long patientId, Long professionalId, DiagnosisEntity diagnosisEntity) {
        Optional<PatientEntity> existingPatient = patientService.getPatientById(patientId);
        Optional<ProfessionalEntity> existingProfessional= professionalService.getProfessionalById(patientId);

        if (existingPatient.isPresent()) {
            diagnosisEntity.setPatient(existingPatient.get());
        } else {
            PatientEntity newPatient = diagnosisEntity.getPatient();
            patientService.save(newPatient);
            diagnosisEntity.setPatient(newPatient);
        }

        if (existingProfessional.isPresent()) {
            diagnosisEntity.setProfessional(existingProfessional.get());
        } else {
            ProfessionalEntity newProfessional = diagnosisEntity.getProfessional();
            professionalService.save(newProfessional);
            diagnosisEntity.setProfessional(newProfessional);
        }

        return diagnosisRepository.save(diagnosisEntity);
    }

    @Override
    public Optional<DiagnosisEntity> getById(Long id) {
        return diagnosisRepository.findById(id);
    }

    @Override
    public List<DiagnosisEntity> findAll() {
        return StreamSupport.stream(diagnosisRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DiagnosisEntity> findAll(Pageable pageable) {
        return diagnosisRepository.findAll(pageable);
    }

    @Override
    public boolean isExists(Long id) {
        return diagnosisRepository.existsById(id);
    }

    @Override
    public DiagnosisEntity partialUpdate(Long id, DiagnosisEntity diagnosisEntity) {
        diagnosisEntity.setId(id);
        return diagnosisRepository.save(diagnosisEntity);
    }

    @Override
    public void delete(Long id) {
        diagnosisRepository.deleteById(id);
    }
}
