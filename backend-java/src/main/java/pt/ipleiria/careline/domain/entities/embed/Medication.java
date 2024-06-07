package pt.ipleiria.careline.domain.entities.embed;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Medication {
    private String name;
    private String dosage;
    private String frequency;
    private String duration;

    public Medication() {
    }

    public Medication(String name, String dosage, String frequency, String duration) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
    }
}
