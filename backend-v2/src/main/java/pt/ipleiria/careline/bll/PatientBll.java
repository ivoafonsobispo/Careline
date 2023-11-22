package pt.ipleiria.careline.bll;

import org.springframework.stereotype.Controller;
import pt.ipleiria.careline.dal.PatientRepository;
import pt.ipleiria.careline.dto.PatientDTO;
import pt.ipleiria.careline.entities.users.Patient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PatientBll {

    private final PatientRepository patientRepository;

    public PatientBll(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> getAll() {
        return patientRepository.findAll()
                .stream()
                .map(patient -> new PatientDTO(patient.getName(), patient.getEmail()))
                .collect(Collectors.toList());
    }

    public Optional<PatientDTO> getById(Integer id) {
        return patientRepository.findById(id)
                .map(patient -> new PatientDTO(patient.getName(), patient.getEmail()));
    }

    public Optional<PatientDTO> update(Integer id, Patient patient) {
        return patientRepository.findById(id)
                .map(patientToUpdate -> {
                    patientToUpdate.setName(patient.getName());
                    patientToUpdate.setEmail(patient.getEmail());
                    patientToUpdate.setPassword(patient.getPassword());
                    Patient updatedPatient = patientRepository.save(patientToUpdate);
                    return new PatientDTO(updatedPatient.getName(), updatedPatient.getEmail());
                });
    }

    public PatientDTO create(Patient patient) {
        try {
            Patient createdPatient = patientRepository.save(patient);
            return new PatientDTO(createdPatient.getName(), createdPatient.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create patient", e);
        }
    }

    public void delete(Integer id) {
        patientRepository.deleteById(id);
    }
}
