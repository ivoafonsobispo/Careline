package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.responses.PatientResponseDTO;
import pt.ipleiria.careline.domain.dto.responses.ProfessionalResponseDTO;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class ProfessionalResponseMapper implements Mapper<ProfessionalEntity, ProfessionalResponseDTO> {

    private ModelMapper modelMapper;

    public ProfessionalResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfessionalResponseDTO mapToDTO(ProfessionalEntity professionalEntity) {
        return modelMapper.map(professionalEntity, ProfessionalResponseDTO.class);
    }

    @Override
    public ProfessionalEntity mapFrom(ProfessionalResponseDTO professionalDTO) {
        return modelMapper.map(professionalDTO, ProfessionalEntity.class);
    }
}
