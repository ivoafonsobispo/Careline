package pt.ipleiria.careline.utils;

import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvGenerator {
    private static final String CSV_HEADER_HEARTBEAT = "HeartbeatID,PatientID,Heartbeat\n";
    private static final String CSV_HEADER_TEMPERATURE = "TemperatureID,PatientID,Temperature\n";

    public void generateHeartbeatCsv(List<HeartbeatEntity> heartbeatEntities) {
        StringBuilder csv = new StringBuilder();
        csv.append(CSV_HEADER_HEARTBEAT);

        for (HeartbeatEntity heartbeatEntity : heartbeatEntities) {
            csv.append(heartbeatEntity.getPatient().getId());
            csv.append(",");
            csv.append(heartbeatEntity.getId());
            csv.append(",");
            csv.append(heartbeatEntity.getHeartbeat());
            csv.append("\n");
        }

        String desktopPath = System.getProperty("user.home") + "/Desktop";
        Path desktopDirectory = Paths.get(desktopPath);
        String fileName = "heartbeat_data.csv";
        Path filePath = desktopDirectory.resolve(fileName);

        try {
            Files.writeString(filePath, csv.toString());
            System.out.println("CSV file exported to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateTemperatureCsv(List<TemperatureEntity> temperatureEntities) {
        StringBuilder csv = new StringBuilder();
        csv.append(CSV_HEADER_TEMPERATURE);

        for (TemperatureEntity temperatureEntity : temperatureEntities) {
            csv.append(temperatureEntity.getId());
            csv.append(",");
            csv.append(temperatureEntity.getPatient().getId());
            csv.append(",");
            csv.append(temperatureEntity.getTemperature());
            csv.append("\n");
        }

        String desktopPath = System.getProperty("user.home") + "/Desktop";
        Path desktopDirectory = Paths.get(desktopPath);
        String fileName = "temperature_data.csv";
        Path filePath = desktopDirectory.resolve(fileName);

        try {
            Files.writeString(filePath, csv.toString());
            System.out.println("CSV file exported to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
