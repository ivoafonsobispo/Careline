package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class PatientMapperImpl implements Mapper<PatientEntity, PatientDTO> {

    private ModelMapper modelMapper;

    public PatientMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PatientDTO mapToDTO(PatientEntity patientEntity) {
        return modelMapper.map(patientEntity, PatientDTO.class);
    }

    @Override
    public PatientEntity mapFrom(PatientDTO patientDTO) {
        return modelMapper.map(patientDTO, PatientEntity.class);
    }
}
