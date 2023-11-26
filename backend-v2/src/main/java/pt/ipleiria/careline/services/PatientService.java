package pt.ipleiria.careline.services;

import pt.ipleiria.careline.domain.entities.users.PatientEntity;

public interface PatientService {
    PatientEntity createPatient(PatientEntity patientEntity);

    PatientEntity getPatientById(Integer id);
}
