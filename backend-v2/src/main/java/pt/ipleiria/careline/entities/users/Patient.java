package pt.ipleiria.careline.entities.users;

import jakarta.persistence.Entity;

@Entity
public class Patient extends User {

    public Patient() {
    }

    public Patient(String name, String email, String password, String nus) {
        super(name, email, password, nus);
    }

}