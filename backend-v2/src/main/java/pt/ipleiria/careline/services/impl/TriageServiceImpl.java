package pt.ipleiria.careline.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.helpers.DataValidation;
import pt.ipleiria.careline.repositories.TriageRepository;
import pt.ipleiria.careline.services.TriageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TriageServiceImpl implements TriageService {

    private TriageRepository triageRepository;


    public TriageServiceImpl(TriageRepository triageRepository) {
        this.triageRepository = triageRepository;
        DataValidation dataValidation = new DataValidation();
    }

    @Override
    public TriageEntity save(TriageEntity triageEntity) {
        validateTriage(triageEntity);
        return triageRepository.save(triageEntity);
    }

    @Override
    public Optional<TriageEntity> getTriageById(Long id) {
        return triageRepository.findById(id);
    }

    @Override
    public List<TriageEntity> findAll() {
        return triageRepository.findAll();
    }

    @Override
    public Page<TriageEntity> findAll(Pageable pageable) {
        return triageRepository.findAll(pageable);
    }

    @Override
    public Optional<TriageEntity> findLastTriage() {
        Optional<TriageEntity> t = triageRepository.findLastTriage();
        return t;
    }

    @Override
    public boolean isExists(Long id) {
        if (!triageRepository.existsById(id))
            return false;
        return true;
    }

    @Override
    public TriageEntity partialUpdate(Long id, TriageEntity triageEntity) {
        triageEntity.setId(id);
        return triageRepository.findById(id).map(existingTriage -> {
            Optional.ofNullable(triageEntity.getHeartbeat()).ifPresent(existingTriage::setHeartbeat);
            Optional.ofNullable(triageEntity.getTemperature()).ifPresent(existingTriage::setTemperature);
            Optional.ofNullable(triageEntity.getSymptoms()).ifPresent(existingTriage::setSymptoms);
            Optional.ofNullable(triageEntity.getPatient()).ifPresent(existingTriage::setPatient);
            Optional.ofNullable(triageEntity.getTagOrder()).ifPresent(existingTriage::setTagOrder);
            Optional.ofNullable(triageEntity.getSeverity()).ifPresent(existingTriage::setSeverity);
            return triageRepository.save(existingTriage);
        }).orElseThrow(() -> new RuntimeException("Triage not found"));
    }

    @Override
    public void delete(Long id) {
        Optional<TriageEntity> opt = triageRepository.findById(id);
        if (opt.isPresent())
            triageRepository.delete(opt.get());
    }

    private void validateTriage(TriageEntity triageEntity) {
        List<String> errors = new ArrayList<>();
        //TODO validar se o cliente existe, integrar com dados de saúde e validar
        if (!(triageRepository.findAllById(triageEntity.getId()).isEmpty()))
            errors.add("Triage Id already exists");
        if (!errors.isEmpty())
            throw new IllegalArgumentException(String.join(", ", errors));
    }
}
