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
    private static final String CSV_HEADER = "PatientID,Heartbeat\n";

    public void generateCsv(List<HeartbeatEntity> heartbeatEntities) {
        StringBuilder csv = new StringBuilder();
        csv.append(CSV_HEADER);

        for (HeartbeatEntity heartbeatEntity : heartbeatEntities) {
            csv.append(heartbeatEntity.getPatient().getId());
            csv.append(",");
            csv.append(heartbeatEntity.getId());
            csv.append(",");
            csv.append(heartbeatEntity.getHeartbeat());
            csv.append("\n");
        }

        // Specify the desktop path
        String desktopPath = System.getProperty("user.home") + "/Desktop";

        // Create a Path object for the desktop directory
        Path desktopDirectory = Paths.get(desktopPath);

        // Specify the file name and path
        String fileName = "heartbeat_data.csv";
        Path filePath = desktopDirectory.resolve(fileName);

        // Write the CSV data to the file
        try {
            Files.writeString(filePath, csv.toString());
            System.out.println("CSV file exported to: " + filePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
