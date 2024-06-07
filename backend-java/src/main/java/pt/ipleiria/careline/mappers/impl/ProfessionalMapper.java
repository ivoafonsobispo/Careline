package pt.ipleiria.careline.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pt.ipleiria.careline.domain.dto.ProfessionalDTO;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.mappers.Mapper;

@Component
public class ProfessionalMapper implements Mapper<ProfessionalEntity, ProfessionalDTO> {

    private ModelMapper modelMapper;

    public ProfessionalMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfessionalDTO mapToDTO(ProfessionalEntity professionalEntity) {
        return modelMapper.map(professionalEntity, ProfessionalDTO.class);
    }

    @Override
    public ProfessionalEntity mapFrom(ProfessionalDTO professionalDTO) {
        return modelMapper.map(professionalDTO, ProfessionalEntity.class);
    }
}
