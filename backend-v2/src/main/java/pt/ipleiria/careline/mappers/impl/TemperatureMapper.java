package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.HealthData.TemperatureDTO;
import pt.ipleiria.careline.domain.entities.healthdata.TemperatureEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class TemperatureMapper implements Mapper<TemperatureEntity, TemperatureDTO> {

    private ModelMapper modelMapper;

    public TemperatureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TemperatureDTO mapToDTO(TemperatureEntity temperatureEntity) {
        return modelMapper.map(temperatureEntity, TemperatureDTO.class);
    }

    @Override
    public TemperatureEntity mapFrom(TemperatureDTO temperatureDTO) {
        return modelMapper.map(temperatureDTO, TemperatureEntity.class);
    }
}
