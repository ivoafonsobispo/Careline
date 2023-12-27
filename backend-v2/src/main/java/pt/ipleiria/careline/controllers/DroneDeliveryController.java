package pt.ipleiria.careline.controllers;

import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.DiagnosisDTO;
import pt.ipleiria.careline.domain.dto.DroneDeliveryDTO;
import pt.ipleiria.careline.domain.dto.data.HeartbeatDTO;
import pt.ipleiria.careline.domain.dto.responses.*;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.DroneDeliveryEntity;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.DiagnosisService;
import pt.ipleiria.careline.services.DroneDeliveryService;
import pt.ipleiria.careline.utils.PdfGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Optional;

@RequestMapping("/api/patients/{patientId}/deliveries")
@RestController
@CrossOrigin
public class DroneDeliveryController {
    private DroneDeliveryService service;
    private Mapper<DroneDeliveryEntity, DroneDeliveryDTO> mapper;
    private Mapper<DroneDeliveryEntity, DroneDeliveryResponseDTO> responseMapper;
    private Mapper<PatientEntity, PatientResponseDTO> patientResponseMapper;
    private SimpMessagingTemplate messagingTemplate;

    public DroneDeliveryController(DroneDeliveryService service, Mapper<DroneDeliveryEntity, DroneDeliveryDTO> mapper, Mapper<DroneDeliveryEntity, DroneDeliveryResponseDTO> responseMapper, Mapper<PatientEntity, PatientResponseDTO> patientResponseMapper, SimpMessagingTemplate messagingTemplate) {
        this.service = service;
        this.mapper = mapper;
        this.responseMapper = responseMapper;
        this.patientResponseMapper = patientResponseMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<DroneDeliveryResponseDTO> create(@PathVariable("patientId") Long patientId, @RequestBody @Valid DroneDeliveryDTO droneDTO) {
        DroneDeliveryEntity entity = mapper.mapFrom(droneDTO);
        DroneDeliveryEntity savedEntity = service.save(patientId, entity);
        DroneDeliveryResponseDTO responseDTO = new DroneDeliveryResponseDTO(savedEntity.getId(),Instant.now(),patientResponseMapper.mapToDTO(savedEntity.getPatient()),savedEntity.getPrescriptions(),savedEntity.getDeliveryStatus(),savedEntity.getDepartureTime(),savedEntity.getArrivalTime(),savedEntity.getCoordinate());

        messagingTemplate.convertAndSend("/topic/deliveries",responseDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public Page<DroneDeliveryResponseDTO> listDeliveries(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<DroneDeliveryEntity> deliveries = service.findAll(pageable, patientId);
        return deliveries.map(delivery -> new DroneDeliveryResponseDTO(delivery.getId(),delivery.getCreatedAt(),patientResponseMapper.mapToDTO(delivery.getPatient()),delivery.getPrescriptions(),delivery.getDeliveryStatus(),delivery.getDepartureTime(),delivery.getArrivalTime(),delivery.getCoordinate()));
    }

    @GetMapping("/latest")
    public Page<DroneDeliveryResponseDTO> listLatest(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<DroneDeliveryEntity> deliveries = service.findAllLatest(pageable, patientId);
        return deliveries.map(delivery -> new DroneDeliveryResponseDTO(delivery.getId(),delivery.getCreatedAt(),patientResponseMapper.mapToDTO(delivery.getPatient()),delivery.getPrescriptions(),delivery.getDeliveryStatus(),delivery.getDepartureTime(),delivery.getArrivalTime(),delivery.getCoordinate()));
    }

    @GetMapping("/date/{date}")
    public Page<DroneDeliveryResponseDTO> listDeliveryByDate(@PathVariable("patientId") Long patientId, @PathVariable("date") String date, Pageable pageable) {
        Page<DroneDeliveryEntity> deliveries = service.findAllByDate(pageable, patientId, date);
        return deliveries.map(delivery -> new DroneDeliveryResponseDTO(delivery.getId(),delivery.getCreatedAt(),patientResponseMapper.mapToDTO(delivery.getPatient()),delivery.getPrescriptions(),delivery.getDeliveryStatus(),delivery.getDepartureTime(),delivery.getArrivalTime(),delivery.getCoordinate()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DroneDeliveryResponseDTO> getDeliveryById(@PathVariable("patientId") Long patientId,@PathVariable("id") Long deliveryId) {
        Optional<DroneDeliveryEntity> delivery = service.getById(patientId,deliveryId);
        return delivery.map(deliveryEntity -> {
            DroneDeliveryResponseDTO responseDTO = responseMapper.mapToDTO(deliveryEntity);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DroneDeliveryResponseDTO> partialUpdate(@PathVariable("id") Long id, @RequestBody @Valid DroneDeliveryResponseDTO dto) {
        if (!service.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DroneDeliveryEntity entity = responseMapper.mapFrom(dto);
        DroneDeliveryEntity savedEntity = service.partialUpdate(id, entity);
        return new ResponseEntity<>(responseMapper.mapToDTO(savedEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (!service.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
