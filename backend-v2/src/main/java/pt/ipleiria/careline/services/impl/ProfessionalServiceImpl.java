package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.helpers.UserValidation;
import pt.ipleiria.careline.repositories.ProfessionalRepository;
import pt.ipleiria.careline.services.ProfessionalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Override
    public ProfessionalEntity save(ProfessionalEntity professionalEntity) {
        validateProfessional(professionalEntity);
        return professionalRepository.save(professionalEntity);
    }

    @Override
    public Optional<ProfessionalEntity> getProfessionalById(Long id) {
        return professionalRepository.findById(id);
    }

    @Override
    public Optional<ProfessionalEntity> getProfessionalByNus(String nus) {
        return professionalRepository.findByNus(nus);
    }

    @Override
    public List<ProfessionalEntity> findAll() {
        return StreamSupport.stream(professionalRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProfessionalEntity> findAll(Pageable pageable) {
        return professionalRepository.findAll(pageable);
    }

    @Override
    public boolean isExists(Long id) {
        return professionalRepository.existsById(id);
    }

    @Override
    public ProfessionalEntity partialUpdate(Long id, ProfessionalEntity professionalEntity) {
        professionalEntity.setId(id);
        return professionalRepository.findById(id).map(existingProfessional -> {
            Optional.ofNullable(professionalEntity.getName()).ifPresent(existingProfessional::setName);
            Optional.ofNullable(professionalEntity.getEmail()).ifPresent(existingProfessional::setEmail);
            Optional.ofNullable(professionalEntity.getPassword()).ifPresent(existingProfessional::setPassword);
            Optional.ofNullable(professionalEntity.getNus()).ifPresent(existingProfessional::setNus);
            return professionalRepository.save(existingProfessional);
        }).orElseThrow(() -> new RuntimeException("Professional not found"));
    }

    @Override
    public void delete(Long id) {
        professionalRepository.deleteById(id);
    }

    private void validateProfessional(ProfessionalEntity professionalEntity) {
        List<String> errors = new ArrayList<>();
        if (professionalRepository.findByNus(professionalEntity.getNus()).isPresent())
            errors.add("NUS already exists");
        if (professionalRepository.findByEmail(professionalEntity.getEmail()).isPresent())
            errors.add("Email already exists");
        if (!UserValidation.isNusValid(professionalEntity.getNus()))
            errors.add("Invalid NUS");
        if (!errors.isEmpty())
            throw new IllegalArgumentException(String.join(", ", errors));
    }
}
