package pt.ipleiria.careline.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.enums.Sex;

import java.sql.Date;

@Entity
public class Professionals extends User {
    @NotNull
    private String hospital;


    public Professionals() {
    }

    public Professionals(String name, String email, String password, Sex sex, Date birthDate, String nus, Date created_at, boolean active, int version, String hospital) {
        super(name, email, password, sex, birthDate, nus, created_at, active, version);
        this.hospital = hospital;
    }

    public Professionals(Integer id, String name, String email, String password, Sex sex, Date birthDate, String nus, Date created_at, boolean active, int version, String hospital) {
        super(id, name, email, password, sex, birthDate, nus, created_at, active, version);
        this.hospital = hospital;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}