package pt.ipleiria.careline.domain.entities.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.security.core.userdetails.UserDetails;
import pt.ipleiria.careline.domain.enums.Role;

import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
public abstract class UserEntity implements UserDetails {
    @CurrentTimestamp
    @Column(name = "created_at")
    public Instant createdAt;
    @Enumerated(EnumType.STRING)
    Role role;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_id_seq")
    private Long id;
    @NotNull(message = "Name is required")
    private String name;
    @Column(unique = true, nullable = false)
    @NotNull(message = "Email is required")
    @Email
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    @NotNull(message = "Password is required")
    private String password;
    @Column(unique = true, nullable = false)
    @NotNull(message = "NUS is required")
    @Size(min = 9, max = 9, message = "NUS must be exactly 9 characters")
    private String nus;

    public UserEntity() {
    }

    public UserEntity(String name, String nus, String email, String password, Role role) {
        this.name = name;
        this.nus = nus;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
