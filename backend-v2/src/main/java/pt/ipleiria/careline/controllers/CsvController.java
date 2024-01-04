package pt.ipleiria.careline.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ipleiria.careline.domain.entities.TriageEntity;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.TemperatureService;
import pt.ipleiria.careline.services.TriageService;
import pt.ipleiria.careline.utils.CsvGenerator;
import pt.ipleiria.careline.utils.ZipFileGenerator;

import java.util.List;

@RequestMapping("/api/csv")
@RestController
@CrossOrigin
public class CsvController {

    private final HeartbeatService heartbeatService;
    private final TemperatureService temperatureService;
    private final TriageService triageService;

    public CsvController(HeartbeatService heartbeatService, TemperatureService temperatureService, TriageService triageService) {
        this.heartbeatService = heartbeatService;
        this.temperatureService = temperatureService;
        this.triageService = triageService;
    }

    @GetMapping("/heartbeats")
    public ResponseEntity<String> getHeartbeatsCsv() {
        List<HeartbeatEntity> heartbeatEntityList = heartbeatService.findAll();
        List<TriageEntity> triageEntitiesList = triageService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();
        String heartbeatCsv = csvGenerator.generateHeartbeatCsv(heartbeatEntityList, triageEntitiesList);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=heartbeats_data.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

        return ResponseEntity.ok().headers(headers).body(heartbeatCsv);
    }

    @GetMapping("/temperatures")
    public ResponseEntity<String> getTemperaturesCsv() {
        List<TemperatureEntity> temperatureEntities = temperatureService.findAll();
        List<TriageEntity> triageEntitiesList = triageService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();
        String temperatureCsv = csvGenerator.generateTemperatureCsv(temperatureEntities, triageEntitiesList);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=temperatures_data.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

        return ResponseEntity.ok().headers(headers).body(temperatureCsv);
    }

    @GetMapping
    public ResponseEntity<byte[]> getCsv() {
        List<HeartbeatEntity> heartbeatEntityList = heartbeatService.findAll();
        List<TemperatureEntity> temperatureEntities = temperatureService.findAll();
        List<TriageEntity> triageEntities = triageService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();

        String heartbeatCsv = csvGenerator.generateHeartbeatCsv(heartbeatEntityList, triageEntities);
        String temperatureCsv = csvGenerator.generateTemperatureCsv(temperatureEntities, triageEntities);

        ZipFileGenerator zipFileGenerator = new ZipFileGenerator();
        byte[] zipFile = zipFileGenerator.createZipFile(heartbeatCsv, temperatureCsv);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.zip");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

        return ResponseEntity.ok().headers(headers).body(zipFile);
    }

}
