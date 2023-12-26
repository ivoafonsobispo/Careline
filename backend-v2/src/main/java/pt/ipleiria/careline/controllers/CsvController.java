package pt.ipleiria.careline.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.TemperatureService;
import pt.ipleiria.careline.utils.CsvGenerator;
import pt.ipleiria.careline.utils.ZipFileGenerator;

import java.util.List;

@RequestMapping("/api/csv")
@RestController
@CrossOrigin
public class CsvController {

    private HeartbeatService heartbeatService;
    private TemperatureService temperatureService;

    public CsvController(HeartbeatService heartbeatService, TemperatureService temperatureService) {
        this.heartbeatService = heartbeatService;
        this.temperatureService = temperatureService;
    }

    @GetMapping("/heartbeats")
    public ResponseEntity<String> getHeartbeatsCsv() {
        List<HeartbeatEntity> heartbeatEntityList = heartbeatService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();
        String heartbeatCsv = csvGenerator.generateHeartbeatCsv(heartbeatEntityList);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=heartbeats_data.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

        return ResponseEntity.ok().headers(headers).body(heartbeatCsv);
    }

    @GetMapping("/temperatures")
    public ResponseEntity<String> getTemperaturesCsv() {
        List<TemperatureEntity> temperatureEntities= temperatureService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();
        String temperatureCsv = csvGenerator.generateTemperatureCsv(temperatureEntities);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=temperatures_data.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

        return ResponseEntity.ok().headers(headers).body(temperatureCsv);
    }

    @GetMapping
    public ResponseEntity<byte[]> getCsv() {
        List<HeartbeatEntity> heartbeatEntityList = heartbeatService.findAll();
        List<TemperatureEntity> temperatureEntities= temperatureService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();

        String heartbeatCsv = csvGenerator.generateHeartbeatCsv(heartbeatEntityList);
        String temperatureCsv = csvGenerator.generateTemperatureCsv(temperatureEntities);

        ZipFileGenerator zipFileGenerator = new ZipFileGenerator();
        byte[] zipFile = zipFileGenerator.createZipFile(heartbeatCsv,temperatureCsv);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.zip");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

        return ResponseEntity.ok().headers(headers).body(zipFile);
    }

}
