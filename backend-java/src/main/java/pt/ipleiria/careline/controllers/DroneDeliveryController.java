package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.DroneDeliveryDTO;
import pt.ipleiria.careline.domain.dto.responses.DroneDeliveryResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.entities.DroneDeliveryEntity;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.DroneDeliveryService;

import java.time.Instant;
import java.util.Optional;

@RequestMapping("/api/patients/{patientId}")
@RestController
@CrossOrigin
public class DroneDeliveryController {
    private final DroneDeliveryService service;
    private final Mapper<DroneDeliveryEntity, DroneDeliveryDTO> mapper;
    private final Mapper<DroneDeliveryEntity, DroneDeliveryResponseDTO> responseMapper;
    private final Mapper<PatientEntity, PatientResponseDTO> patientResponseMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public DroneDeliveryController(DroneDeliveryService service, Mapper<DroneDeliveryEntity, DroneDeliveryDTO> mapper, Mapper<DroneDeliveryEntity, DroneDeliveryResponseDTO> responseMapper, Mapper<PatientEntity, PatientResponseDTO> patientResponseMapper, SimpMessagingTemplate messagingTemplate) {
        this.service = service;
        this.mapper = mapper;
        this.responseMapper = responseMapper;
        this.patientResponseMapper = patientResponseMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/diagnosis/{diagnosisId}/deliveries")
    @PreAuthorize("hasRole('PROFESSIONAL') or hasRole('PATIENT')")
    public ResponseEntity<DroneDeliveryResponseDTO> create(@PathVariable("patientId") Long patientId, @PathVariable("diagnosisId") Long diagnosisId, @RequestBody @Valid DroneDeliveryDTO droneDTO) {
        DroneDeliveryEntity entity = mapper.mapFrom(droneDTO);
        DroneDeliveryEntity savedEntity = service.save(patientId, diagnosisId, entity);
        DroneDeliveryResponseDTO responseDTO = new DroneDeliveryResponseDTO(savedEntity.getId(), Instant.now(), patientResponseMapper.mapToDTO(savedEntity.getPatient()), savedEntity.getMedications(), savedEntity.getDeliveryStatus(), savedEntity.getDepartureTime(), savedEntity.getArrivalTime(), savedEntity.getCoordinate());

        messagingTemplate.convertAndSend("/topic/deliveries", responseDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/deliveries")
    @PreAuthorize("hasRole('PATIENT')")
    public Page<DroneDeliveryResponseDTO> listDeliveries(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<DroneDeliveryEntity> deliveries = service.findAll(pageable, patientId);
        return deliveries.map(delivery -> new DroneDeliveryResponseDTO(delivery.getId(), delivery.getCreatedAt(), patientResponseMapper.mapToDTO(delivery.getPatient()), delivery.getMedications(), delivery.getDeliveryStatus(), delivery.getDepartureTime(), delivery.getArrivalTime(), delivery.getCoordinate()));
    }

    @GetMapping("/deliveries/latest")
    @PreAuthorize("hasRole('PATIENT')")
    public Page<DroneDeliveryResponseDTO> listLatest(@PathVariable("patientId") Long patientId, Pageable pageable) {
        Page<DroneDeliveryEntity> deliveries = service.findAllLatest(pageable, patientId);
        return deliveries.map(delivery -> new DroneDeliveryResponseDTO(delivery.getId(), delivery.getCreatedAt(), patientResponseMapper.mapToDTO(delivery.getPatient()), delivery.getMedications(), delivery.getDeliveryStatus(), delivery.getDepartureTime(), delivery.getArrivalTime(), delivery.getCoordinate()));
    }

    @GetMapping("/deliveries/date/{date}")
    @PreAuthorize("hasRole('PATIENT')")
    public Page<DroneDeliveryResponseDTO> listDeliveryByDate(@PathVariable("patientId") Long patientId, @PathVariable("date") String date, Pageable pageable) {
        Page<DroneDeliveryEntity> deliveries = service.findAllByDate(pageable, patientId, date);
        return deliveries.map(delivery -> new DroneDeliveryResponseDTO(delivery.getId(), delivery.getCreatedAt(), patientResponseMapper.mapToDTO(delivery.getPatient()), delivery.getMedications(), delivery.getDeliveryStatus(), delivery.getDepartureTime(), delivery.getArrivalTime(), delivery.getCoordinate()));
    }

    @GetMapping("/deliveries/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<DroneDeliveryResponseDTO> getDeliveryById(@PathVariable("patientId") Long patientId, @PathVariable("id") Long deliveryId) {
        Optional<DroneDeliveryEntity> delivery = service.getById(patientId, deliveryId);
        return delivery.map(savedEntity -> {
            DroneDeliveryResponseDTO responseDTO = new DroneDeliveryResponseDTO(savedEntity.getId(), savedEntity.getCreatedAt(), patientResponseMapper.mapToDTO(savedEntity.getPatient()), savedEntity.getMedications(), savedEntity.getDeliveryStatus(), savedEntity.getDepartureTime(), savedEntity.getArrivalTime(), savedEntity.getCoordinate());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/deliveries/{id}/in-transit")
    public ResponseEntity<DroneDeliveryResponseDTO> changeStatusToInTransit(@PathVariable("id") Long id) {
        DroneDeliveryEntity savedEntity = service.changeStatusToInTransit(id);
        DroneDeliveryResponseDTO droneDeliveryResponseDTO = new DroneDeliveryResponseDTO(savedEntity.getId(), savedEntity.getCreatedAt(), patientResponseMapper.mapToDTO(savedEntity.getPatient()), savedEntity.getMedications(), savedEntity.getDeliveryStatus(), savedEntity.getDepartureTime(), savedEntity.getArrivalTime(), savedEntity.getCoordinate());
        messagingTemplate.convertAndSend("/topic/deliveries", droneDeliveryResponseDTO);
        return new ResponseEntity<>(droneDeliveryResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/deliveries/{id}/delivered")
    public ResponseEntity<DroneDeliveryResponseDTO> changeStatusToDelivered(@PathVariable("id") Long id) {
        DroneDeliveryEntity savedEntity = service.changeStatusToDelivered(id);
        DroneDeliveryResponseDTO droneDeliveryResponseDTO = new DroneDeliveryResponseDTO(savedEntity.getId(), savedEntity.getCreatedAt(), patientResponseMapper.mapToDTO(savedEntity.getPatient()), savedEntity.getMedications(), savedEntity.getDeliveryStatus(), savedEntity.getDepartureTime(), savedEntity.getArrivalTime(), savedEntity.getCoordinate());
        messagingTemplate.convertAndSend("/topic/deliveries", droneDeliveryResponseDTO);
        return new ResponseEntity<>(droneDeliveryResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/deliveries/{id}/failed")
    public ResponseEntity<DroneDeliveryResponseDTO> changeStatusToFailed(@PathVariable("id") Long id) {
        DroneDeliveryEntity savedEntity = service.changeStatusToFailed(id);
        DroneDeliveryResponseDTO droneDeliveryResponseDTO = new DroneDeliveryResponseDTO(savedEntity.getId(), savedEntity.getCreatedAt(), patientResponseMapper.mapToDTO(savedEntity.getPatient()), savedEntity.getMedications(), savedEntity.getDeliveryStatus(), savedEntity.getDepartureTime(), savedEntity.getArrivalTime(), savedEntity.getCoordinate());
        messagingTemplate.convertAndSend("/topic/deliveries", droneDeliveryResponseDTO);
        return new ResponseEntity<>(droneDeliveryResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deliveries/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (!service.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
