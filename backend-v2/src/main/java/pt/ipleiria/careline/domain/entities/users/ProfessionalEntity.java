package pt.ipleiria.careline.domain.entities.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professionals")
public class ProfessionalEntity extends UserEntity {
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "professional_patient",
            joinColumns = { @JoinColumn(name = "professional_id") },
            inverseJoinColumns = { @JoinColumn(name = "patient_id") }
    )
    private List<PatientEntity> patients;
    @ManyToMany
    private List<TriageEntity> triages;

    public ProfessionalEntity() {
    }

    public ProfessionalEntity(String name, String email, String password, String nus) {
        super(name, email, password, nus);
        patients = new ArrayList<>();
    }

    public List<PatientEntity> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientEntity> patients) {
        this.patients = patients;
    }

    public List<TriageEntity> getTriages() {
        return triages;
    }

    public void setTriages(List<TriageEntity> triages) {
        this.triages = triages;
    }
}