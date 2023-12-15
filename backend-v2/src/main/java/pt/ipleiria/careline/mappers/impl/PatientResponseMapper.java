package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class PatientResponseMapper implements Mapper<PatientEntity, PatientResponseDTO> {

    private ModelMapper modelMapper;

    public PatientResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PatientResponseDTO mapToDTO(PatientEntity patientEntity) {
        return modelMapper.map(patientEntity, PatientResponseDTO.class);
    }

    @Override
    public PatientEntity mapFrom(PatientResponseDTO patientDTO) {
        return modelMapper.map(patientDTO, PatientEntity.class);
    }
}
