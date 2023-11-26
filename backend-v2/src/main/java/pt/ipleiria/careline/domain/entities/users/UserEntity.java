package pt.ipleiria.careline.domain.entities.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.Instant;

@MappedSuperclass
public class UserEntity {
    @CurrentTimestamp
    @Column(name = "created_at")
    public Instant createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_id_seq")
    private Integer id;
    @NotNull
    private String name;
    @Column(unique = true, nullable = false)
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Column(unique = true, nullable = false)
    @NotNull
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
