package pt.ipleiria.careline;

import pt.ipleiria.careline.domain.dto.HeartbeatDTO;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.dto.ProfessionalDTO;
import pt.ipleiria.careline.domain.dto.TemperatureDTO;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.healthdata.TemperatureEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;

import java.time.Instant;

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

    public static PatientDTO createPatientDTOA() {
        PatientDTO patientEntity = new PatientDTO();
        patientEntity.setName("Ivo Bispo");
        patientEntity.setEmail("ivo.bispo@gmail.com");
        patientEntity.setPassword("password");
        patientEntity.setNus("123456789");
        return patientEntity;
    }

    public static PatientEntity createPatientDTOB() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName("Ana Martins");
        patientEntity.setEmail("ana.martins@gmail.com");
        patientEntity.setPassword("password");
        patientEntity.setNus("987654321");
        return patientEntity;
    }

    public static ProfessionalEntity createProfessionalEntityA() {
        ProfessionalEntity professionalEntity = new ProfessionalEntity();
        professionalEntity.setName("Ivo Bispo");
        professionalEntity.setEmail("ivo.bispo@gmail.com");
        professionalEntity.setPassword("password");
        professionalEntity.setNus("123456789");
        return professionalEntity;
    }

    public static ProfessionalEntity createProfessionalEntityB() {
        ProfessionalEntity professionalEntity = new ProfessionalEntity();
        professionalEntity.setName("Ana Martins");
        professionalEntity.setEmail("ana.martins@gmail.com");
        professionalEntity.setPassword("password");
        professionalEntity.setNus("987654321");
        return professionalEntity;
    }

    public static ProfessionalDTO createProfessionalDTOA() {
        ProfessionalDTO professionalDTO = new ProfessionalDTO();
        professionalDTO.setName("Ivo Bispo");
        professionalDTO.setEmail("ivo.bispo@gmail.com");
        professionalDTO.setPassword("password");
        professionalDTO.setNus("123456789");
        return professionalDTO;
    }

    public static ProfessionalDTO createProfessionalDTOB() {
        ProfessionalDTO professionalDTO = new ProfessionalDTO();
        professionalDTO.setName("Ana Martins");
        professionalDTO.setEmail("ana.martins@gmail.com");
        professionalDTO.setPassword("password");
        professionalDTO.setNus("987654321");
        return professionalDTO;
    }

    public static HeartbeatEntity createHeartbeatEntityA(final PatientEntity patientEntity) {
        HeartbeatEntity heartbeatEntity = new HeartbeatEntity();
        heartbeatEntity.setPatient(patientEntity);
        heartbeatEntity.setHeartbeat(80);
        heartbeatEntity.setCreatedAt(Instant.now());
        return heartbeatEntity;
    }

    public static HeartbeatDTO createHeartbeatDTOA(final PatientDTO patientDTO) {
        HeartbeatDTO heartbeatEntity = new HeartbeatDTO();
        heartbeatEntity.setPatient(patientDTO);
        heartbeatEntity.setHeartbeat(80);
        heartbeatEntity.setCreatedAt(Instant.now());
        return heartbeatEntity;
    }

    public static TemperatureEntity createTemperatureEntityA(final PatientEntity patientEntity) {
        TemperatureEntity temperatureEntity = new TemperatureEntity();
        temperatureEntity.setPatient(patientEntity);
        temperatureEntity.setTemperature(36);
        temperatureEntity.setCreatedAt(Instant.now());
        return temperatureEntity;
    }

    public static TemperatureDTO createTemperatureDTOA(final PatientDTO patientDTO) {
        TemperatureDTO temperatureEntity = new TemperatureDTO();
        temperatureEntity.setPatient(patientDTO);
        temperatureEntity.setTemperature(36);
        temperatureEntity.setCreatedAt(Instant.now());
        return temperatureEntity;
    }
}