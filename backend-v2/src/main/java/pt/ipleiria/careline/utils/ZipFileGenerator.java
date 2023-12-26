package pt.ipleiria.careline.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileGenerator {
    public byte[] createZipFile(String heartbeatCsv, String temperatureCsv) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            // Add heartbeat CSV to the ZIP file
            addToZipFile(zipOutputStream, "heartbeat_data.csv", heartbeatCsv.getBytes());

            // Add temperature CSV to the ZIP file
            addToZipFile(zipOutputStream, "temperature_data.csv", temperatureCsv.getBytes());

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }

    private void addToZipFile(ZipOutputStream zipOutputStream, String fileName, byte[] content) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(content);
        zipOutputStream.closeEntry();
    }
}
