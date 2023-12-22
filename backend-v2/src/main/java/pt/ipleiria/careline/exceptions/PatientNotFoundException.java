package pt.ipleiria.careline.exceptions;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String nus) {
        super("Could not find patient with nus: " + nus);
    }

    public PatientNotFoundException(Long id) {
        super("Could not find patient with id: " + id);
    }

    public PatientNotFoundException(String email, String password) {
        super("Could not find patient with email: " + email + " and password: " + password);
    }

    public PatientNotFoundException() {
        super("Could not find patient");
    }
}
