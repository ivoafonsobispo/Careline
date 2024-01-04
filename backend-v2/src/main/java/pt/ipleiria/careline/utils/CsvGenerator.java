package pt.ipleiria.careline.utils;

import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;

import java.util.List;

public class CsvGenerator {
    private static final String CSV_HEADER_HEARTBEAT = "PatientID,HeartbeatID,Heartbeat,Severity\n";
    private static final String CSV_HEADER_TEMPERATURE = "PatientID,TemperatureID,Temperature,Severity\n";

    public String generateHeartbeatCsv(List<HeartbeatEntity> heartbeatEntities, List<TriageEntity> triageEntities) {
        StringBuilder csv = new StringBuilder();
        csv.append(CSV_HEADER_HEARTBEAT);

        for (HeartbeatEntity heartbeatEntity : heartbeatEntities) {
            csv.append(heartbeatEntity.getPatient().getId());
            csv.append(",");
            csv.append(heartbeatEntity.getId());
            csv.append(",");
            csv.append(heartbeatEntity.getHeartbeat());
            csv.append(",");
            csv.append(heartbeatEntity.getSeverity());
            csv.append("\n");
        }

        for (TriageEntity triageEntity : triageEntities) {
            csv.append(triageEntity.getPatient().getId());
            csv.append(",");
            csv.append(triageEntity.getId());
            csv.append(",");
            csv.append(triageEntity.getHeartbeat());
            csv.append(",");
            csv.append(triageEntity.getSeverity());
            csv.append("\n");
        }

        return csv.toString();
    }

    public String generateTemperatureCsv(List<TemperatureEntity> temperatureEntities, List<TriageEntity> triageEntities) {
        StringBuilder csv = new StringBuilder();
        csv.append(CSV_HEADER_TEMPERATURE);

        for (TemperatureEntity temperatureEntity : temperatureEntities) {
            csv.append(temperatureEntity.getPatient().getId());
            csv.append(",");
            csv.append(temperatureEntity.getId());
            csv.append(",");
            csv.append(temperatureEntity.getTemperature());
            csv.append(",");
            csv.append(temperatureEntity.getSeverity());
            csv.append("\n");
        }

        for (TriageEntity triageEntity : triageEntities) {
            csv.append(triageEntity.getPatient().getId());
            csv.append(",");
            csv.append(triageEntity.getId());
            csv.append(",");
            csv.append(triageEntity.getTemperature());
            csv.append(",");
            csv.append(triageEntity.getSeverity());
            csv.append("\n");
        }

        return csv.toString();
    }
}
