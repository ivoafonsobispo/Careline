package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.data.TemperatureDTO;
import pt.ipleiria.careline.domain.dto.data.TriageDTO;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class TriageMapper implements Mapper<TriageEntity, TriageDTO> {

    private ModelMapper modelMapper;

    public TriageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TriageDTO mapToDTO(TriageEntity triageEntity) {
        return modelMapper.map(triageEntity, TriageDTO.class);
    }

    @Override
    public TriageEntity mapFrom(TriageDTO triageDTO) {
        return modelMapper.map(triageDTO, TriageEntity.class);
    }
}

