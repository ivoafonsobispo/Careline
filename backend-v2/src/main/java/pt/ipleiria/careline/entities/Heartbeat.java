package pt.ipleiria.careline.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

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
}
