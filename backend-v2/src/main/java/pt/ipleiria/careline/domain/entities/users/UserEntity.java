package pt.ipleiria.careline.domain.entities.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;

@MappedSuperclass
public abstract class UserEntity {
    @CurrentTimestamp
    @Column(name = "created_at")
    public Instant createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_id_seq")
    private Long id;
    @NotNull(message = "Name is required")
    private String name;
    @Column(unique = true, nullable = false)
    @NotNull(message = "Email is required")
    @Email
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 8, max = 30)
    private String password;
    @Column(unique = true, nullable = false)
    @NotNull(message = "NUS is required")
    @Size(min = 9, max = 9)
    private String nus;

    public UserEntity() {
    }

    public UserEntity(String name, String email, String password, String nus) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nus = nus;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNus() {
        return nus;
    }

    public void setNus(String nus) {
        this.nus = nus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
