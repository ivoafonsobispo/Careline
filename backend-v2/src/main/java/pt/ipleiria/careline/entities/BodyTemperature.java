package pt.ipleiria.careline.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@Entity
public class BodyTemperature {
    @Id
    @SequenceGenerator(name = "bodytemperature_id_sequence", sequenceName = "bodytemperature_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bodytemperature_id_sequence")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Patient patient;

    @NotNull
    private Integer temperature;

    @CreatedDate
    private Date created_at;

    public BodyTemperature() {
    }

    public BodyTemperature(Patient patient, @NotNull Integer temperature, Date created_at) {
        this.patient = patient;
        this.temperature = temperature;
        this.created_at = created_at;
    }

    public BodyTemperature(Integer id, Patient patient, @NotNull Integer temperature, Date created_at) {
        this.id = id;
        this.patient = patient;
        this.temperature = temperature;
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

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
