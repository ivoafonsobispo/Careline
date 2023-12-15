package pt.ipleiria.careline.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.services.HeartbeatService;
import pt.ipleiria.careline.services.TemperatureService;
import pt.ipleiria.careline.utils.CsvGenerator;

import java.util.List;

@RequestMapping("/api/csv")
@RestController
public class CsvController {

    private HeartbeatService heartbeatService;
    private TemperatureService temperatureService;

    public CsvController(HeartbeatService heartbeatService, TemperatureService temperatureService) {
        this.heartbeatService = heartbeatService;
        this.temperatureService = temperatureService;
    }

    @GetMapping("/heartbeats")
    public void getHeartbeatsCsv() {
        List<HeartbeatEntity> heartbeatEntityList = heartbeatService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();
        csvGenerator.generateHeartbeatCsv(heartbeatEntityList);
    }

    @GetMapping("/temperatures")
    public void getTemperaturesCsv() {
        List<TemperatureEntity> temperatureEntities= temperatureService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();
        csvGenerator.generateTemperatureCsv(temperatureEntities);
    }

    @GetMapping
    public void getCsv() {
        List<HeartbeatEntity> heartbeatEntityList = heartbeatService.findAll();
        List<TemperatureEntity> temperatureEntities= temperatureService.findAll();
        CsvGenerator csvGenerator = new CsvGenerator();
        csvGenerator.generateHeartbeatCsv(heartbeatEntityList);
        csvGenerator.generateTemperatureCsv(temperatureEntities);
    }

}
