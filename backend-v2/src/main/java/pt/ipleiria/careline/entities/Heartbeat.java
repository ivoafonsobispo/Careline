package pt.ipleiria.careline.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@Entity
public class Heartbeat {
    @Id
    @SequenceGenerator(name = "heartbeat_id_sequence", sequenceName = "heartbeat_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "heartbeat_id_sequence")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Patient patient;

    @NotNull
    private Integer heartbeat;

    @CreatedDate
    private Date created_at;

    public Heartbeat() {
    }

    public Heartbeat(Patient patient, @NotNull Integer heartbeat, Date created_at) {
        this.patient = patient;
        this.heartbeat = heartbeat;
        this.created_at = created_at;
    }

    public Heartbeat(Integer id, Patient patient, @NotNull Integer heartbeat, Date created_at) {
        this.id = id;
        this.patient = patient;
        this.heartbeat = heartbeat;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
