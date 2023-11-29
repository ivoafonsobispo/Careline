package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.dto.data.TemperatureDTO;
import pt.ipleiria.careline.domain.dto.data.responses.TemperatureResponseDTO;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.TemperatureService;

import java.util.Optional;

@RequestMapping("/api/patients/{patientId}/temperatures")
@RestController
public class TemperatureController {

    private Mapper<TemperatureEntity, TemperatureDTO> temperatureMapper;
    private Mapper<PatientEntity, PatientDTO> patientMapper;
    private TemperatureService temperatureService;

    public TemperatureController(Mapper<TemperatureEntity, TemperatureDTO> temperatureMapper, TemperatureService temperatureService, Mapper<PatientEntity, PatientDTO> patientMapper) {
        this.temperatureMapper = temperatureMapper;
        this.patientMapper = patientMapper;
        this.temperatureService = temperatureService;
    }

    @PostMapping
    public ResponseEntity<TemperatureDTO> createTemperature(@PathVariable("patientId") Long patientId, @RequestBody @Valid TemperatureDTO temperatureDTO) {
        TemperatureEntity temperatureEntity = temperatureMapper.mapFrom(temperatureDTO);
        temperatureEntity.setPatient(patientMapper.mapFrom(temperatureDTO.getPatient()));
        TemperatureEntity createdHeartbeat = temperatureService.create(patientId, temperatureEntity);
        TemperatureDTO createdHeartbeatDTO = temperatureMapper.mapToDTO(createdHeartbeat);
        return new ResponseEntity<>(createdHeartbeatDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public Page<TemperatureResponseDTO> listTemperatures(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<TemperatureEntity> temperatureEntities = temperatureService.findAll(pageable, patientId);
        return temperatureEntities.map(temperature -> new TemperatureResponseDTO(temperature.getTemperature(), temperature.getCreatedAt()));
    }

    @GetMapping("/latest")
    public Page<TemperatureResponseDTO> listLatestTemperatures(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<TemperatureEntity> temperatureEntities = temperatureService.findAllLatest(pageable, patientId);
        return temperatureEntities.map(temperature -> new TemperatureResponseDTO(temperature.getTemperature(), temperature.getCreatedAt()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemperatureDTO> getTemperatureById(@PathVariable("id") Long id) {
        Optional<TemperatureEntity> temperature = temperatureService.getById(id);
        return temperature.map(temperatureEntity -> {
            TemperatureDTO temperatureDTO = temperatureMapper.mapToDTO(temperatureEntity);
            return new ResponseEntity<>(temperatureDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (!temperatureService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        temperatureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
