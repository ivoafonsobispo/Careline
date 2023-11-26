package pt.ipleiria.careline.domain.entities.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class PatientEntity extends UserEntity {

    public PatientEntity() {
    }

    public PatientEntity(String name, String email, String password, String nus) {
        super(name, email, password, nus);
    }

}