package pt.ipleiria.careline.dto;

public class PatientDTO {
    public String name;
    public String email;
    public String nus;

    public PatientDTO(String name, String email, String nus) {
        this.name = name;
        this.email = email;
        this.nus = nus;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNus() {
        return nus;
    }
}
