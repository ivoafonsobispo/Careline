package pt.ipleiria.careline.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipleiria.careline.bll.PatientBll;
import pt.ipleiria.careline.dto.PatientDTO;
import pt.ipleiria.careline.entities.users.Patient;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/patient")
@RestController
public class PatientAPI {

    private final PatientBll patientBll;

    public PatientAPI(PatientBll patientBll) {
        this.patientBll = patientBll;
    }

    @GetMapping
    public List<PatientDTO> getAll() {
        return patientBll.getAll();
    }

    @PostMapping
    public ResponseEntity<PatientDTO> create(@RequestBody @Valid Patient patient) {
        PatientDTO createdPatient = patientBll.create(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PatientDTO>> getById(@PathVariable("id") Integer id) {
        Optional<PatientDTO> patient = patientBll.getById(id);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<PatientDTO>> update(@PathVariable("id") Integer id, @RequestBody @Valid Patient patient) {
        Optional<PatientDTO > updatedPatient = patientBll.update(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        patientBll.delete(id);
        return ResponseEntity.noContent().build();
    }
}
