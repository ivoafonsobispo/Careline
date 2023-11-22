package pt.ipleiria.careline.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ipleiria.careline.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
