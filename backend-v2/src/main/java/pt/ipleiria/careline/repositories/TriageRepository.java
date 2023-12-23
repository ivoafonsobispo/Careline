package pt.ipleiria.careline.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TriageRepository extends JpaRepository<TriageEntity, Long>,
        PagingAndSortingRepository<TriageEntity, Long> {
    //get triage by id
    Optional<TriageEntity> findById(Long triageId);

    List<TriageEntity> findAllById( Long trisgeId);

    //get all triage data units at some time
    Page<TriageEntity> findAllByCreatedAt(Pageable pageable, Instant instant);

    @Query("SELECT t FROM TriageEntity t WHERE t.createdAt = (SELECT MAX(t2.createdAt) FROM TriageEntity t2)")
    Optional<TriageEntity> findLastTriage();

    @Query("SELECT t FROM TriageEntity t WHERE t.patient = patient AND t.createdAt = (SELECT MAX(t2.createdAt) FROM TriageEntity t2)")
    Optional<TriageEntity> findLastParientTriage( @Param("patient") PatientEntity patient);

    @Query("SELECT t FROM TriageEntity t WHERE t.patient = :patient")
    Page<TriageEntity> getTriagesByPatient(Pageable pageable, @Param("patient") PatientEntity patient);

    @Query("SELECT t FROM TriageEntity t WHERE t.patient = :patient AND t.id = :triageId")
    Optional<TriageEntity> getTriageByPatient( @Param("patient") PatientEntity patient, @Param("triageId") Long triageId);



}
