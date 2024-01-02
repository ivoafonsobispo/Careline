package pt.ipleiria.careline.exceptions;

public class PatientException extends RuntimeException {
    public PatientException(String nus) {
        super("Could not find patient with nus: " + nus);
    }

    public PatientException(Long id) {
        super("Could not find patient with id: " + id);
    }

    public PatientException(String email, String password) {
        super("Could not find patient with email: " + email + " and password: " + password);
    }

    public PatientException() {
        super("Could not find patient");
    }
}
