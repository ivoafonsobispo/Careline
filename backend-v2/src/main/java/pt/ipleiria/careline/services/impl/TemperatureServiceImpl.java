package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.helpers.DataValidation;
import pt.ipleiria.careline.repositories.TemperatureRepository;
import pt.ipleiria.careline.services.PatientService;
import pt.ipleiria.careline.services.TemperatureService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TemperatureServiceImpl implements TemperatureService {

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
}
