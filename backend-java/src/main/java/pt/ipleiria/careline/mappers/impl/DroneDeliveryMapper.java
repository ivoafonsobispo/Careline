package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.DiagnosisDTO;
import pt.ipleiria.careline.domain.dto.DroneDeliveryDTO;
import pt.ipleiria.careline.domain.entities.DiagnosisEntity;
import pt.ipleiria.careline.domain.entities.DroneDeliveryEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class DroneDeliveryMapper implements Mapper<DroneDeliveryEntity, DroneDeliveryDTO> {

    private ModelMapper modelMapper;

    public DroneDeliveryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DroneDeliveryDTO mapToDTO(DroneDeliveryEntity entity) {
        return modelMapper.map(entity, DroneDeliveryDTO.class);
    }

    @Override
    public DroneDeliveryEntity mapFrom(DroneDeliveryDTO dto) {
        return modelMapper.map(dto, DroneDeliveryEntity.class);
    }
}
