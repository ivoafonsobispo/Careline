package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.DiagnosisDTO;
import pt.ipleiria.careline.domain.dto.responses.DiagnosisResponseDTO;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class DiagnosisResponseMapper implements Mapper<DiagnosisEntity,
        DiagnosisResponseDTO> {

    private ModelMapper modelMapper;

    public DiagnosisResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DiagnosisResponseDTO mapToDTO(DiagnosisEntity diagnosisEntity) {
        return modelMapper.map(diagnosisEntity, DiagnosisResponseDTO.class);
    }

    @Override
    public DiagnosisEntity mapFrom(DiagnosisResponseDTO diagnosisDTO) {
        return modelMapper.map(diagnosisDTO, DiagnosisEntity.class);
    }
}
