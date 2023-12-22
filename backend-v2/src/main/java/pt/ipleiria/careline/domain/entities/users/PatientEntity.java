package pt.ipleiria.careline.domain.entities.users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class PatientEntity extends UserEntity {
    @ManyToMany(mappedBy = "patients")
    List<ProfessionalEntity> professionals;

    public PatientEntity() {
    }

    public PatientEntity(String name, String email, String password, String nus) {
        super(name, email, password, nus);
        professionals = new ArrayList<>();
    }

    public List<ProfessionalEntity> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<ProfessionalEntity> professionals) {
        this.professionals = professionals;
    }
}