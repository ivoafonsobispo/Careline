package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.responses.TriageResponseDTO;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class TriageResponseMapper implements Mapper<TriageEntity, TriageResponseDTO> {

    private final ModelMapper modelMapper;

    public TriageResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TriageResponseDTO mapToDTO(TriageEntity triageEntity) {
        return modelMapper.map(triageEntity, TriageResponseDTO.class);
    }

    @Override
    public TriageEntity mapFrom(TriageResponseDTO triageDTO) {
        return modelMapper.map(triageDTO, TriageEntity.class);
    }
}

