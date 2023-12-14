package pt.ipleiria.careline.domain.entities.users;

import jakarta.persistence.*;
import pt.ipleiria.careline.domain.entities.data.TriageEntity;

import java.util.List;

@Entity
@Table(name = "professionals")
public class ProfessionalEntity extends UserEntity {

    @ManyToMany
    private List<TriageEntity> triages;

    public ProfessionalEntity() {
    }

    public ProfessionalEntity(String name, String email, String password, String nus) {
        super(name, email, password, nus);
    }

}