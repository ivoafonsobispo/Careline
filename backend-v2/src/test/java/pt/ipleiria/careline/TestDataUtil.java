package pt.ipleiria.careline;

import pt.ipleiria.careline.domain.entities.users.PatientEntity;

public class TestDataUtil {
    private TestDataUtil() {
    }

    public static PatientEntity createPatientEntityA() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName("Ivo Bispo");
        patientEntity.setEmail("ivo.bispo@gmail.com");
        patientEntity.setPassword("password");
        patientEntity.setNus("123456789");
        return patientEntity;
    }

    public static PatientEntity createPatientEntityB() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName("Ana Martins");
        patientEntity.setEmail("ana.martins@gmail.com");
        patientEntity.setPassword("password");
        patientEntity.setNus("987654321");
        return patientEntity;
    }
}