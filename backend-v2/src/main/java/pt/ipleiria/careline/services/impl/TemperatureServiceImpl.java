package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.enums.Severity;
import pt.ipleiria.careline.utils.DateConversionUtil;
import pt.ipleiria.careline.validations.DataValidation;
import pt.ipleiria.careline.repositories.TemperatureRepository;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.TemperatureService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TemperatureServiceImpl implements TemperatureService {
    private static final double GOOD_MIN = 36.5;
    private static final double GOOD_MAX = 37.3;
    private static final double MEDIUM_MIN = 35;
    private static final double MEDIUM_MAX = 36.4;
    private static final double MEDIUM_MAX_UPPER = 39.9;
    private TemperatureRepository temperatureRepository;
    private PatientService patientService;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository, PatientService patientService) {
        this.temperatureRepository = temperatureRepository;
        this.patientService = patientService;
        DataValidation dataValidation = new DataValidation();
    }

    @Override
    public TemperatureEntity create(Long patientId, TemperatureEntity temperature) {
        if (!DataValidation.isTemperatureValid(temperature.getTemperature())) {
            throw new IllegalArgumentException("Temperature is not valid");
        }

        Optional<PatientEntity> existingPatient = patientService.getPatientById(patientId);
        if (existingPatient.isPresent()) {
            temperature.setPatient(existingPatient.get());
        } else {
            throw new IllegalArgumentException("Patient not found");
        }

        Severity severity = getSeverityCategory(temperature.getTemperature());
        temperature.setSeverity(severity);

        return temperatureRepository.save(temperature);
    }

    @Override
    public List<TemperatureEntity> findAll() {
        return StreamSupport.stream(temperatureRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TemperatureEntity> findAll(Pageable pageable, Long patientId) {
        return temperatureRepository.findAllByPatientId(pageable, patientId);
    }

    @Override
    public Page<TemperatureEntity> findAllLatest(Pageable pageable, Long patientId) {
        return temperatureRepository.findAllByPatientIdOrderByCreatedAtDesc(pageable, patientId);
    }

    @Override
    public Optional<TemperatureEntity> getById(Long id) {
        return temperatureRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return temperatureRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        temperatureRepository.deleteById(id);
    }

    @Override
    public Page<TemperatureEntity> findAllByDate(Pageable pageable, Long patientId, String date) {
        DateConversionUtil dateConversionUtil = new DateConversionUtil();
        Instant startDate = dateConversionUtil.convertStringToStartOfDayInstant(date);
        Instant endDate = dateConversionUtil.convertStringToEndOfDayInstant(date);

        return temperatureRepository.findAllByPatientIdAndCreatedAtBetweenOrderByCreatedAtDesc(
                pageable, patientId, startDate, endDate);
    }


    private Severity getSeverityCategory(double temperature) {
        if (temperature >= GOOD_MIN && temperature <= GOOD_MAX) {
            return Severity.GOOD;
        } else if ((temperature >= MEDIUM_MIN && temperature <= MEDIUM_MAX) || (temperature >= GOOD_MAX + 1 && temperature <= MEDIUM_MAX_UPPER)) {
            return Severity.MEDIUM;
        } else {
            return Severity.CRITICAL;
        }
    }
}
