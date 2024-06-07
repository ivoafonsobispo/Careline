package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.exceptions.PatientException;
import pt.ipleiria.careline.exceptions.ProfessionalException;
import pt.ipleiria.careline.repositories.PatientRepository;
import pt.ipleiria.careline.repositories.ProfessionalRepository;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.ProfessionalService;
import pt.ipleiria.careline.services.TemperatureService;
import pt.ipleiria.careline.validations.UserValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {
    private final ProfessionalRepository professionalRepository;
    private final PatientRepository patientRepository;
    private final PatientService patientService;
    private final HeartbeatService heartbeatService;
    private final TemperatureService temperatureService;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository, PatientRepository patientRepository, PatientService patientService, HeartbeatService heartbeatService, TemperatureService temperatureService) {
        this.professionalRepository = professionalRepository;
        this.patientRepository = patientRepository;
        this.patientService = patientService;
        this.heartbeatService = heartbeatService;
        this.temperatureService = temperatureService;
    }

    @Override
    public ProfessionalEntity save(ProfessionalEntity professionalEntity) {
        validateProfessional(professionalEntity);
        return professionalRepository.save(professionalEntity);
    }

    @Override
    public Optional<ProfessionalEntity> getProfessionalById(Long id) {
        return professionalRepository.findById(id);
    }

    @Override
    public Optional<ProfessionalEntity> getProfessionalByNus(String nus) {
        return professionalRepository.findByNus(nus);
    }

    @Override
    public List<ProfessionalEntity> findAll() {
        return StreamSupport.stream(professionalRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProfessionalEntity> findAll(Pageable pageable) {
        return professionalRepository.findAll(pageable);
    }

    @Override
    public boolean isExists(Long id) {
        return professionalRepository.existsById(id);
    }

    @Override
    public ProfessionalEntity partialUpdate(Long id, ProfessionalEntity professionalEntity) {
        professionalEntity.setId(id);
        return professionalRepository.findById(id).map(existingProfessional -> {
            Optional.ofNullable(professionalEntity.getName()).ifPresent(existingProfessional::setName);
            Optional.ofNullable(professionalEntity.getEmail()).ifPresent(existingProfessional::setEmail);
            Optional.ofNullable(professionalEntity.getPassword()).ifPresent(existingProfessional::setPassword);
            Optional.ofNullable(professionalEntity.getNus()).ifPresent(existingProfessional::setNus);
            Optional.ofNullable(professionalEntity.getPatients()).ifPresent(existingProfessional::setPatients);
            return professionalRepository.save(existingProfessional);
        }).orElseThrow(ProfessionalException::new);
    }

    @Override
    public void delete(Long id) {
        professionalRepository.deleteById(id);
    }

    @Override
    public void setPatientToProfessional(Long professionalId, Long patientId) {
        ProfessionalEntity professional = professionalRepository.findById(professionalId).orElseThrow(ProfessionalException::new);
        PatientEntity patient = patientRepository.findById(patientId).orElseThrow(PatientException::new);

        professional.getPatients().add(patient);
        patientService.setProfessionalToPatient(professional, patient);

        partialUpdate(professionalId, professional);
    }

    @Override
    public Page<PatientEntity> getProfessionalPatients(Long professionalId, Pageable pageable) {
        ProfessionalEntity professional = professionalRepository.findById(professionalId).orElseThrow(ProfessionalException::new);
        return patientRepository.findByProfessionalsId(professional.getId(), pageable);
    }

    @Override
    public Page<PatientEntity> getAvailablePatient(Long professionalId, Pageable pageable) {
        professionalRepository.findById(professionalId).orElseThrow(ProfessionalException::new);
        return patientRepository.findByProfessionalsIdIsNull(pageable);
    }

    @Override
    public PatientEntity getPatientById(Long professionalId, Long patientId) {
        professionalRepository.findById(professionalId).orElseThrow(ProfessionalException::new);
        return patientService.getPatientById(patientId).orElseThrow(PatientException::new);
    }

    private void validateProfessional(ProfessionalEntity professionalEntity) {
        List<String> errors = new ArrayList<>();
        if (professionalRepository.findByNus(professionalEntity.getNus()).isPresent())
            errors.add("NUS already exists");
        if (professionalRepository.findByEmail(professionalEntity.getEmail()).isPresent())
            errors.add("Email already exists");
        if (!UserValidation.isNusValid(professionalEntity.getNus()))
            errors.add("Invalid NUS");
        if (!errors.isEmpty())
            throw new IllegalArgumentException(String.join(", ", errors));
    }
}
