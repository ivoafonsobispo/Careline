package pt.ipleiria.careline.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.DroneDeliveryEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Delivery;
import pt.ipleiria.careline.exceptions.DiagnosisException;
import pt.ipleiria.careline.exceptions.DroneException;
import pt.ipleiria.careline.exceptions.PatientException;
import pt.ipleiria.careline.repositories.DroneDeliveryRepository;
import pt.ipleiria.careline.services.DiagnosisService;
import pt.ipleiria.careline.services.DroneDeliveryService;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.utils.DateConversionUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class DroneDeliveryServiceImpl implements DroneDeliveryService {

    private final DroneDeliveryRepository repository;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;

    public DroneDeliveryServiceImpl(DroneDeliveryRepository repository, PatientService patientService, DiagnosisService diagnosisService) {
        this.repository = repository;
        this.patientService = patientService;
        this.diagnosisService = diagnosisService;
    }

    private static void deliveryBelongsToPatient(Long patientId, Page<DroneDeliveryEntity> droneDeliveryEntities) {
        if (droneDeliveryEntities.get().anyMatch(delivery -> !Objects.equals(delivery.getPatient().getId(), patientId))) {
            log.atError().log("Delivery does not belong to patient - {}", patientId);
            throw new DroneException("Delivery does not belong to patient");
        }
    }

    @Override
    public DroneDeliveryEntity save(Long patientId, Long diagnosisId, DroneDeliveryEntity delivery) {
        Optional<PatientEntity> existingPatient = patientService.getPatientById(patientId);
        if (existingPatient.isPresent()) {
            delivery.setPatient(existingPatient.get());
        } else {
            log.atError().log("Patient does not exist - {}", patientId);
            throw new PatientException();
        }

        Optional<DiagnosisEntity> diagnosis = diagnosisService.getById(diagnosisId);
        if (diagnosis.isPresent()) {
            List<String> medications = new ArrayList<>();
            for (int i = 0; i < diagnosis.get().getMedications().size(); i++) {
                medications.add(diagnosis.get().getMedications().get(i).getName());
            }
            delivery.setMedications(medications);
        } else {
            log.atError().log("Diagnosis does not exist - {}", diagnosisId);
            throw new DiagnosisException();
        }

        if (diagnosis.get().getPatient() != existingPatient.get()) {
            log.atError().log("Diagnosis does not belong to patient - {}", patientId);
            throw new DiagnosisException("Diagnosis does not belong to patient");
        }

        delivery.setDeliveryStatus(Delivery.PENDING);
        delivery.setArrivalTime(Instant.EPOCH);
        delivery.setDepartureTime(Instant.EPOCH);

        log.info("Created Delivery - {}", delivery);

        return repository.save(delivery);
    }

    @Override
    public Optional<DroneDeliveryEntity> getById(Long patientId, Long deliveryId) {
        log.info("Get Delivery by id - {}", deliveryId);
        return repository.findById(deliveryId);
    }

    @Override
    public List<DroneDeliveryEntity> findAll() {
        log.info("Get all Deliveries");
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Page<DroneDeliveryEntity> findAll(Pageable pageable, Long patientId) {
        Page<DroneDeliveryEntity> droneDeliveryEntities = repository.findAllByPatientId(pageable, patientId);
        deliveryBelongsToPatient(patientId, droneDeliveryEntities);

        log.info("Get all Deliveries of Patient - {}", patientId);

        return droneDeliveryEntities;
    }

    @Override
    public boolean isExists(Long id) {
        log.info("Check if Delivery exists - {}", id);
        return repository.existsById(id);
    }

    @Override
    public DroneDeliveryEntity changeStatusToInTransit(Long id) {
        Optional<DroneDeliveryEntity> delivery = repository.findById(id);
        if (delivery.isEmpty()) {
            log.atError().log("Delivery does not exist - {}", id);
            throw new PatientException();
        }

        if (delivery.get().getDeliveryStatus() != Delivery.PENDING) {
            log.atError().log("Delivery status must be PENDING - {}", id);
            throw new DroneException("Delivery status must be PENDING");
        }

        delivery.get().setDeliveryStatus(Delivery.IN_TRANSIT);
        delivery.get().setDepartureTime(Instant.now());
        log.info("Changed Delivery status to IN_TRANSIT - {}", id);
        return repository.save(delivery.get());
    }

    @Override
    public DroneDeliveryEntity changeStatusToDelivered(Long id) {
        Optional<DroneDeliveryEntity> delivery = repository.findById(id);
        if (delivery.isEmpty()) {
            log.atError().log("Delivery does not exist - {}", id);
            throw new PatientException();
        }

        if (delivery.get().getDeliveryStatus() != Delivery.IN_TRANSIT) {
            log.atError().log("Delivery status must be IN_TRANSIT - {}", id);
            throw new DroneException("Delivery status must be IN_TRANSIT");
        }

        delivery.get().setDeliveryStatus(Delivery.DELIVERED);
        delivery.get().setArrivalTime(Instant.now());
        log.atInfo().log("Changed Delivery status to DELIVERED - {}", id);
        return repository.save(delivery.get());
    }

    @Override
    public DroneDeliveryEntity changeStatusToFailed(Long id) {
        Optional<DroneDeliveryEntity> delivery = repository.findById(id);
        if (delivery.isEmpty()) {
            log.atError().log("Delivery does not exist - {}", id);
            throw new PatientException();
        }

        if (delivery.get().getDeliveryStatus() != Delivery.IN_TRANSIT && delivery.get().getDeliveryStatus() != Delivery.PENDING) {
            log.atError().log("Delivery status must be IN_TRANSIT or PENDING - {}", id);
            throw new DroneException("Delivery status must be IN_TRANSIT or PENDING");
        }

        delivery.get().setDeliveryStatus(Delivery.FAILED);
        delivery.get().setArrivalTime(Instant.now());
        log.atInfo().log("Changed Delivery status to FAILED - {}", id);
        return repository.save(delivery.get());
    }

    @Override
    public void delete(Long id) {
        log.info("Delete Delivery - {}", id);
        repository.deleteById(id);
    }

    @Override
    public Page<DroneDeliveryEntity> findAllLatest(Pageable pageable, Long patientId) {
        Page<DroneDeliveryEntity> droneDeliveryEntities = repository.findByPatientIdOrderByCreatedAtDesc(patientId, pageable);
        deliveryBelongsToPatient(patientId, droneDeliveryEntities);

        log.info("Get all Deliveries of Patient - {}", patientId);
        return droneDeliveryEntities;
    }

    @Override
    public Page<DroneDeliveryEntity> findAllByDate(Pageable pageable, Long patientId, String date) {
        DateConversionUtil dateConversionUtil = new DateConversionUtil();
        Instant startDate = dateConversionUtil.convertStringToStartOfDayInstant(date);
        Instant endDate = dateConversionUtil.convertStringToEndOfDayInstant(date);

        log.atInfo().log("Get all Deliveries of Patient - {}", patientId);
        return repository.findAllByPatientIdAndCreatedAtBetweenOrderByCreatedAtDesc(pageable, patientId, startDate, endDate);
    }
}
