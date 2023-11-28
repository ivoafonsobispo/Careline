package pt.ipleiria.careline.services;

import pt.ipleiria.careline.domain.entities.healthdata.TemperatureEntity;

import java.util.Optional;

public interface TemperatureService extends HealthDataService<TemperatureEntity> {

    Optional<TemperatureEntity> getTemperatureById(Long id);

}
