package pt.ipleiria.careline.exceptions;

public class ProfessionalException extends RuntimeException {
    public ProfessionalException(String nus) {
        super("Could not find professional with nus: " + nus);
    }

    public ProfessionalException(Long id) {
        super("Could not find professional with id: " + id);
    }

    public ProfessionalException(String email, String password) {
        super("Could not find professional with email: " + email + " and password: " + password);
    }

    public ProfessionalException() {
        super("Could not find professional");
    }
}
