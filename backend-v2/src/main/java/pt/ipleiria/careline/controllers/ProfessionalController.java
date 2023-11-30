package pt.ipleiria.careline.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.ProfessionalDTO;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.ProfessionalService;

import java.util.Optional;

@RequestMapping("/api/professionals")
@RestController
public class ProfessionalController {

    private final ProfessionalService professionalService;
    private final Mapper<ProfessionalEntity, ProfessionalDTO> professionalMapper;

    public ProfessionalController(ProfessionalService professionalService, Mapper<ProfessionalEntity, ProfessionalDTO> professionalMapper) {
        this.professionalService = professionalService;
        this.professionalMapper = professionalMapper;
    }

    @PostMapping
    public ResponseEntity<ProfessionalDTO> create(@RequestBody @Valid ProfessionalDTO professionalDTO) {
        ProfessionalEntity professionalEntity = professionalMapper.mapFrom(professionalDTO);
        ProfessionalEntity savedProfessionalEntity = professionalService.save(professionalEntity);
        return new ResponseEntity<>(professionalMapper.mapToDTO(savedProfessionalEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ProfessionalDTO> listProfessionals(Pageable pageable) {
        Page<ProfessionalEntity> professionalEntities = professionalService.findAll(pageable);
        return professionalEntities.map(professionalMapper::mapToDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalDTO> getProfessionalById(@PathVariable("id") Long id) {
        Optional<ProfessionalEntity> professional = professionalService.getProfessionalById(id);
        return professional.map(professionalEntity -> {
            ProfessionalDTO professionalDTO = professionalMapper.mapToDTO(professionalEntity);
            return new ResponseEntity<>(professionalDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nus/{nus}")
    public ResponseEntity<ProfessionalDTO> getProfessionalsById(@PathVariable("nus") String nus) {
        Optional<ProfessionalEntity> professional = professionalService.getProfessionalByNus(nus);
        return professional.map(professionalEntity -> {
            ProfessionalDTO professionalDTO = professionalMapper.mapToDTO(professionalEntity);
            return new ResponseEntity<>(professionalDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionalDTO> fullUpdateProfessional(@PathVariable("id") Long id, @RequestBody @Valid ProfessionalDTO professionalDTO) {
        if (!professionalService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        professionalDTO.setId(id);
        ProfessionalEntity professionalEntity = professionalMapper.mapFrom(professionalDTO);
        ProfessionalEntity savedProfessionalEntity = professionalService.save(professionalEntity);
        return new ResponseEntity<>(
                professionalMapper.mapToDTO(savedProfessionalEntity), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProfessionalDTO> partialUpdateProfessional(@PathVariable("id") Long id, @RequestBody @Valid ProfessionalDTO professionalDTO) {
        if (!professionalService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProfessionalEntity professionalEntity = professionalMapper.mapFrom(professionalDTO);
        ProfessionalEntity savedProfessionalEntity = professionalService.partialUpdate(id, professionalEntity);
        return new ResponseEntity<>(
                professionalMapper.mapToDTO(savedProfessionalEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProfessional(@PathVariable("id") Long id) {
        if (!professionalService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        professionalService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


