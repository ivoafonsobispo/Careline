package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.ipleiria.careline.domain.entities.healthdata.TemperatureEntity;

import java.util.List;
import java.util.Optional;

public interface TemperatureService {
    TemperatureEntity createTemperature(Long patientId, TemperatureEntity temperatureEntity);

    List<TemperatureEntity> findAll();

    Page<TemperatureEntity> findAll(Pageable pageable, Long patientId);

    Optional<TemperatureEntity> getTemperatureById(Long id);

    boolean isExists(Long id);

    void delete(Long id);
}
