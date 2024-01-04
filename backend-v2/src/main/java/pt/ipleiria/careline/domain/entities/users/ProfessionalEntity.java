package pt.ipleiria.careline.domain.entities.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pt.ipleiria.careline.domain.entities.TriageEntity;
import pt.ipleiria.careline.domain.enums.Role;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "professionals")
public class ProfessionalEntity extends UserEntity {
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "professional_patient",
            joinColumns = {@JoinColumn(name = "professional_id")},
            inverseJoinColumns = {@JoinColumn(name = "patient_id")}
    )
    private List<PatientEntity> patients;
    @ManyToMany
    private List<TriageEntity> triages;

    public ProfessionalEntity() {
        super();
    }

    public ProfessionalEntity(String name, String nus, String email, String password) {
        super(name, nus, email, password, Role.ROLE_PROFESSIONAL);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return getNus();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}