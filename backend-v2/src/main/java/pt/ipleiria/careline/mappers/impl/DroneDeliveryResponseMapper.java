package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.DroneDeliveryDTO;
import pt.ipleiria.careline.domain.dto.responses.DroneDeliveryResponseDTO;
import pt.ipleiria.careline.domain.entities.DroneDeliveryEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class DroneDeliveryResponseMapper implements Mapper<DroneDeliveryEntity, DroneDeliveryResponseDTO> {

    private ModelMapper modelMapper;

    public DroneDeliveryResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DroneDeliveryResponseDTO mapToDTO(DroneDeliveryEntity entity) {
        return modelMapper.map(entity, DroneDeliveryResponseDTO.class);
    }

    @Override
    public DroneDeliveryEntity mapFrom(DroneDeliveryResponseDTO dto) {
        return modelMapper.map(dto, DroneDeliveryEntity.class);
    }
}
