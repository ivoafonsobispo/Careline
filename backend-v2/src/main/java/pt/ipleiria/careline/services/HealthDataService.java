package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HealthDataService<Entity> {
    Entity create(Long patientId, Entity entity);

    List<Entity> findAll();

    Page<Entity> findAll(Pageable pageable, Long patientId);

    Page<Entity> findAllLatest(Pageable pageable, Long patientId);

    Optional<Entity> getById(Long id);

    boolean isExists(Long id);

    void delete(Long id);
}
