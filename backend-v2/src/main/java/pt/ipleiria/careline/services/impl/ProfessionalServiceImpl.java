package pt.ipleiria.careline.services.impl;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.atError().log("Saving professional - {}", professionalEntity);
        return professionalRepository.save(professionalEntity);
    }

    @Override
    public Optional<ProfessionalEntity> getProfessionalById(Long id) {
        log.atInfo().log("Getting professional by id - {}", id);
        return professionalRepository.findById(id);
    }

    @Override
    public Optional<ProfessionalEntity> getProfessionalByNus(String nus) {
        log.atInfo().log("Getting professional by nus - {}", nus);
        return professionalRepository.findByNus(nus);
    }

    @Override
    public List<ProfessionalEntity> findAll() {
        log.atInfo().log("Getting all professionals");
        return StreamSupport.stream(professionalRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProfessionalEntity> findAll(Pageable pageable) {
        log.atInfo().log("Getting all professionals");
        return professionalRepository.findAll(pageable);
    }

    @Override
    public boolean isExists(Long id) {
        log.atInfo().log("Checking if professional exists - {}", id);
        return professionalRepository.existsById(id);
    }

    @Override
    public ProfessionalEntity partialUpdate(Long id, ProfessionalEntity professionalEntity) {
        professionalEntity.setId(id);
        log.atInfo().log("Partial update professional - {}", professionalEntity);
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
        log.atInfo().log("Delete professional by id - {}", id);
        professionalRepository.deleteById(id);
    }

    @Override
    public void setPatientToProfessional(Long professionalId, Long patientId) {
        ProfessionalEntity professional = professionalRepository.findById(professionalId).orElseThrow(ProfessionalException::new);
        PatientEntity patient = patientRepository.findById(patientId).orElseThrow(PatientException::new);

        professional.getPatients().add(patient);
        patientService.setProfessionalToPatient(professional, patient);

        log.atInfo().log("Set patient to professional - {}", professional);

        partialUpdate(professionalId, professional);
    }

    @Override
    public Page<PatientEntity> getProfessionalPatients(Long professionalId, Pageable pageable) {
        ProfessionalEntity professional = professionalRepository.findById(professionalId).orElseThrow(ProfessionalException::new);
        log.atInfo().log("Get professional patients - {}", professional);
        return patientRepository.findByProfessionalsId(professional.getId(), pageable);
    }

    @Override
    public Page<PatientEntity> getAvailablePatient(Long professionalId, Pageable pageable) {
        professionalRepository.findById(professionalId).orElseThrow(ProfessionalException::new);
        log.atInfo().log("Get available patients");
        return patientRepository.findByProfessionalsIdIsNull(pageable);
    }

    @Override
    public PatientEntity getPatientById(Long professionalId, Long patientId) {
        professionalRepository.findById(professionalId).orElseThrow(ProfessionalException::new);
        return patientService.getPatientById(patientId).orElseThrow(PatientException::new);
    }

    private void validateProfessional(ProfessionalEntity professionalEntity) {
        List<String> errors = new ArrayList<>();
        if (professionalRepository.findByNus(professionalEntity.getNus()).isPresent()) {
            errors.add("NUS already exists");
            log.atError().log("NUS already exists - {}", professionalEntity.getNus());
        }
        if (professionalRepository.findByEmail(professionalEntity.getEmail()).isPresent()) {
            errors.add("Email already exists");
            log.atError().log("Email already exists - {}", professionalEntity.getEmail());
        }
        if (!UserValidation.isNusValid(professionalEntity.getNus())) {
            errors.add("Invalid NUS");
            log.atError().log("Invalid NUS - {}", professionalEntity.getNus());
        }
        if (!errors.isEmpty()) {
            log.atError().log("Invalid professional - {}", professionalEntity);
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}
