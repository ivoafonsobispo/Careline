package pt.ipleiria.careline.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.DroneDeliveryEntity;

import java.util.List;
import java.util.Optional;

public interface DroneDeliveryService {
    DroneDeliveryEntity save(Long patientId, DroneDeliveryEntity delivery);
    Optional<DroneDeliveryEntity> getById(Long patientId,Long deliveryId);
    List<DroneDeliveryEntity> findAll();
    Page<DroneDeliveryEntity> findAll(Pageable pageable, Long patientId);
    boolean isExists(Long id);
    DroneDeliveryEntity partialUpdate(Long id, DroneDeliveryEntity delivery);
    void delete(Long id);
    Page<DroneDeliveryEntity> findAllLatest(Pageable pageable, Long patientId);

    Page<DroneDeliveryEntity> findAllByDate(Pageable pageable, Long patientId, String date);
}
