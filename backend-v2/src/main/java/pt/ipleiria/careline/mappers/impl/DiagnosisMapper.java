package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.DiagnosisDTO;
import pt.ipleiria.careline.domain.dto.ProfessionalDTO;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class DiagnosisMapper implements Mapper<DiagnosisEntity, DiagnosisDTO> {

    private ModelMapper modelMapper;

    public DiagnosisMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DiagnosisDTO mapToDTO(DiagnosisEntity diagnosisEntity) {
        return modelMapper.map(diagnosisEntity, DiagnosisDTO.class);
    }

    @Override
    public DiagnosisEntity mapFrom(DiagnosisDTO diagnosisDTO) {
        return modelMapper.map(diagnosisDTO, DiagnosisEntity.class);
    }
}
