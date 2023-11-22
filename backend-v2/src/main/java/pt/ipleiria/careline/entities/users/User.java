package pt.ipleiria.careline.entities.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import pt.ipleiria.careline.enums.Sex;

import java.sql.Date;

@MappedSuperclass
public class User {
    @Id
    private Integer id;
    @NotNull
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @NotNull
    private String nus;
    @CreatedDate
    private Date created_at;
    @NotNull
    private boolean active;
    @Version
    private int version;

    public User() {
    }

    public User(String name, String email, String password, Sex sex, Date birthDate, String nus, Date created_at, boolean active, int version) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.birthDate = birthDate;
        this.nus = nus;
        this.created_at = created_at;
        this.active = active;
        this.version = version;
    }

    public User(Integer id, String name, String email, String password, Sex sex, Date birthDate, String nus, Date created_at, boolean active, int version) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.birthDate = birthDate;
        this.nus = nus;
        this.created_at = created_at;
        this.active = active;
        this.version = version;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNus() {
        return nus;
    }

    public void setNus(String nus) {
        this.nus = nus;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
