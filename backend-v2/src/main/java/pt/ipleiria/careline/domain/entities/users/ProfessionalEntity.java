package pt.ipleiria.careline.domain.entities.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "professionals")
public class ProfessionalEntity extends UserEntity {

    public ProfessionalEntity() {
    }

    public ProfessionalEntity(String name, String email, String password, String nus) {
        super(name, email, password, nus);
    }

}