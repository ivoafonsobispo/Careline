package pt.ipleiria.careline.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.enums.Sex;

import java.util.Date;

@Entity
public class Professionals {
    @Id
    @SequenceGenerator(name = "professional_id_sequence", sequenceName = "professionals_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professionals_id_sequence")
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
    private String hospital;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @NotNull
    private String nus;
    @NotNull
    private boolean active;
    @Version
    private int version;

    public Professionals() {
    }

    public Professionals(String name, String email, String password, Sex sex, String hospital, Date birthDate, String nus, boolean active, int version) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.hospital = hospital;
        this.birthDate = birthDate;
        this.nus = nus;
        this.active = active;
        this.version = version;
    }

    public Professionals(Integer id, String name, String email, String password, Sex sex, String hospital, Date birthDate, String nus, boolean active, int version) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.hospital = hospital;
        this.birthDate = birthDate;
        this.nus = nus;
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

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
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
