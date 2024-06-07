package pt.ipleiria.careline.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pt.ipleiria.careline.domain.entities.DroneDeliveryEntity;

import java.time.Instant;

@Repository
public interface DroneDeliveryRepository extends JpaRepository<DroneDeliveryEntity, Long>, PagingAndSortingRepository<DroneDeliveryEntity, Long> {
    Page<DroneDeliveryEntity> findAllByPatientId(Pageable pageable, Long patientId);
    Page<DroneDeliveryEntity> findByPatientIdOrderByCreatedAtDesc(Long patientId, Pageable pageable);
    Page<DroneDeliveryEntity> findAllByPatientIdAndCreatedAtBetweenOrderByCreatedAtDesc(Pageable pageable, Long patientId, Instant startDate, Instant endDate);
}
