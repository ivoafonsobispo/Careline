package pt.ipleiria.careline;

import pt.ipleiria.careline.domain.dto.HeartbeatDTO;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;

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

    public static PatientDTO createPatientDTOA() {
        PatientDTO patientEntity = new PatientDTO();
        patientEntity.setName("Ivo Bispo");
        patientEntity.setEmail("ivo.bispo@gmail.com");
        patientEntity.setPassword("password");
        patientEntity.setNus("123456789");
        return patientEntity;
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
}