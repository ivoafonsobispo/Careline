package pt.ipleiria.careline.dto;

public class PatientDTO {
    public String name;
    public String email;

    public PatientDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public PatientDTO() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
