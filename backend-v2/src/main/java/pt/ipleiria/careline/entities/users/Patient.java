package pt.ipleiria.careline.entities.users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.careline.entities.healthdata.Heartbeat;
import pt.ipleiria.careline.entities.healthdata.Temperature;
import pt.ipleiria.careline.enums.Sex;

import java.sql.Date;
import java.util.List;

@Entity
public class Patient extends User {
    @NotNull
    private String address;
    @NotNull
    private String phone;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "patient",cascade = CascadeType.ALL)
    private List<Heartbeat> heartbeats;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "patient",cascade = CascadeType.ALL)
    private List<Temperature> temperatures;

    public Patient() {
    }

    public Patient(String name, String email, String password, Sex sex, Date birthDate, String nus, Date created_at, boolean active, int version, String address, String phone, List<Heartbeat> heartbeats) {
        super(name, email, password, sex, birthDate, nus, created_at, active, version);
        this.address = address;
        this.phone = phone;
        this.heartbeats = heartbeats;
    }

    public Patient(Integer id, String name, String email, String password, Sex sex, Date birthDate, String nus, Date created_at, boolean active, int version, String address, String phone, List<Heartbeat> heartbeats) {
        super(id, name, email, password, sex, birthDate, nus, created_at, active, version);
        this.address = address;
        this.phone = phone;
        this.heartbeats = heartbeats;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Heartbeat> getHeartbeats() {
        return heartbeats;
    }

    public void setHeartbeats(List<Heartbeat> heartbeats) {
        this.heartbeats = heartbeats;
    }
}
