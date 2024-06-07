package pt.ipleiria.careline.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileGenerator {
    public byte[] createZipFile(String heartbeatCsv, String temperatureCsv) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            addToZipFile(zipOutputStream, "heartbeat_data.csv", heartbeatCsv.getBytes());
            addToZipFile(zipOutputStream, "temperature_data.csv", temperatureCsv.getBytes());
            zipOutputStream.close();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
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
