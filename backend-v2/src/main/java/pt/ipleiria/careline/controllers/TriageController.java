package pt.ipleiria.careline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.domain.dto.data.TriageDTO;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.mappers.Mapper;
import pt.ipleiria.careline.services.TriageService;

@RequestMapping("/api/patients/{patientId}/triages")
@RestController
public class TriageController {
    private final Mapper<TriageEntity, TriageDTO> triageMapper;
    private final TriageService triageService;

    public TriageController(Mapper<TriageEntity, TriageDTO> triageMapper, TriageService triageService) {
        this.triageMapper = triageMapper;
        this.triageService = triageService;
    }

}
