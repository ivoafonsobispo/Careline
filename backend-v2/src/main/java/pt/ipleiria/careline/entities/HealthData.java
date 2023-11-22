package pt.ipleiria.careline.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@MappedSuperclass
public class HealthData {
    @Id
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Patient patient;

    @CreatedDate
    private Date created_at;

    public HealthData() {
    }

    public HealthData(Patient patient, Date created_at) {
        this.patient = patient;
        this.created_at = created_at;
    }

    public HealthData(Integer id, Patient patient, Date created_at) {
        this.id = id;
        this.patient = patient;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
