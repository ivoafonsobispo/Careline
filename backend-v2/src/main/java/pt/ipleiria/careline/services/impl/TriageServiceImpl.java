package pt.ipleiria.careline.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.helpers.DataValidation;
import pt.ipleiria.careline.helpers.UserValidation;
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
        return null;
    }

    @Override
    public Optional<TriageEntity> getTriageById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<TriageEntity> findAll() {
        return null;
    }

    @Override
    public Page<TriageEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean isExists(Long id) {
        return false;
    }

    @Override
    public TriageEntity partialUpdate(Long id, TriageEntity triageEntity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
