package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.DroneDeliveryEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.domain.enums.Delivery;
import pt.ipleiria.careline.exceptions.PatientNotFoundException;
import pt.ipleiria.careline.repositories.DiagnosisRepository;
import pt.ipleiria.careline.repositories.DroneDeliveryRepository;
import pt.ipleiria.careline.services.DiagnosisService;
import pt.ipleiria.careline.services.DroneDeliveryService;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.ProfessionalService;
import pt.ipleiria.careline.utils.DateConversionUtil;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DroneDeliveryServiceImpl implements DroneDeliveryService {

    private DroneDeliveryRepository repository;
    private PatientService patientService;

    public DroneDeliveryServiceImpl(DroneDeliveryRepository repository, PatientService patientService) {
        this.repository = repository;
        this.patientService = patientService;
    }

    @Override
    public DroneDeliveryEntity save(Long patientId, DroneDeliveryEntity delivery) {
        Optional<PatientEntity> existingPatient = patientService.getPatientById(patientId);
        if (existingPatient.isPresent()) {
            delivery.setPatient(existingPatient.get());
        } else {
            throw new PatientNotFoundException();
        }

        delivery.setDeliveryStatus(Delivery.PENDING);
        delivery.setArrivalTime(Instant.EPOCH);
        delivery.setDepartureTime(Instant.EPOCH);

        return repository.save(delivery);
    }

    @Override
    public Optional<DroneDeliveryEntity> getById(Long patientId, Long deliveryId) {
        Optional<PatientEntity> existingPatient = patientService.getPatientById(patientId);
        if (existingPatient.isPresent()) {
            return repository.findById(deliveryId);
        } else {
            throw new PatientNotFoundException();
        }
    }

    @Override
    public List<DroneDeliveryEntity> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Page<DroneDeliveryEntity> findAll(Pageable pageable, Long patientId) {
        return repository.findAllByPatientId(pageable,patientId);
    }

    @Override
    public boolean isExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public DroneDeliveryEntity partialUpdate(Long id, DroneDeliveryEntity delivery) {
        delivery.setId(id);
        return repository.save(delivery);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<DroneDeliveryEntity> findAllLatest(Pageable pageable, Long patientId) {
        return repository.findByPatientIdOrderByCreatedAtDesc(patientId,pageable);
    }

    @Override
    public Page<DroneDeliveryEntity> findAllByDate(Pageable pageable, Long patientId, String date) {
        DateConversionUtil dateConversionUtil = new DateConversionUtil();
        Instant startDate = dateConversionUtil.convertStringToStartOfDayInstant(date);
        Instant endDate = dateConversionUtil.convertStringToEndOfDayInstant(date);

        return repository.findAllByPatientIdAndCreatedAtBetweenOrderByCreatedAtDesc(
                pageable, patientId, startDate, endDate);
    }
}
