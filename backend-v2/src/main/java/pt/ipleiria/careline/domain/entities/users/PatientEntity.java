package pt.ipleiria.careline.domain.entities.users;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pt.ipleiria.careline.domain.enums.Role;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "patients")
public class PatientEntity extends UserEntity {
    @ManyToMany(mappedBy = "patients")
    List<ProfessionalEntity> professionals;

    public PatientEntity() {
        super();
    }

    public PatientEntity(String name, String nus, String email, String password) {
        super(name, nus, email, password, Role.ROLE_PATIENT);
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