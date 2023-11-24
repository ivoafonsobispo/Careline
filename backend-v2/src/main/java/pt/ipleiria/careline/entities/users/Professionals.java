package pt.ipleiria.careline.entities.users;

import jakarta.persistence.Entity;

@Entity
public class Professionals extends User {

    public Professionals() {
    }

    public Professionals(String name, String email, String password, String nus) {
        super(name, email, password, nus);
    }

}