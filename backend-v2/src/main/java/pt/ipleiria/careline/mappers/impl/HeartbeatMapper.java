package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.HeartbeatDTO;
import pt.ipleiria.careline.domain.entities.healthdata.HeartbeatEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class HeartbeatMapper implements Mapper<HeartbeatEntity, HeartbeatDTO> {

    private ModelMapper modelMapper;

    public HeartbeatMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public HeartbeatDTO mapToDTO(HeartbeatEntity heartbeatEntity) {
        return modelMapper.map(heartbeatEntity, HeartbeatDTO.class);
    }

    @Override
    public HeartbeatEntity mapFrom(HeartbeatDTO heartbeatDTO) {
        return modelMapper.map(heartbeatDTO, HeartbeatEntity.class);
    }
}
