package pt.ipleiria.careline.utils;

import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;

import java.util.List;

public class CsvGenerator {
    private static final String CSV_HEADER_HEARTBEAT = "PatientID,HeartbeatID,Heartbeat,Severity\n";
    private static final String CSV_HEADER_TEMPERATURE = "PatientID,TemperatureID,Temperature,Severity\n";

    public String generateHeartbeatCsv(List<HeartbeatEntity> heartbeatEntities) {
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

        return csv.toString();
    }

    public String generateTemperatureCsv(List<TemperatureEntity> temperatureEntities) {
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

        return csv.toString();
    }
}
