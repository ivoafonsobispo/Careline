package pt.ipleiria.careline.exceptions;

public class ProfessionalNotFoundException extends RuntimeException {
    public ProfessionalNotFoundException(String nus) {
        super("Could not find professional with nus: " + nus);
    }

    public ProfessionalNotFoundException(Long id) {
        super("Could not find professional with id: " + id);
    }

    public ProfessionalNotFoundException(String email, String password) {
        super("Could not find professional with email: " + email + " and password: " + password);
    }

    public ProfessionalNotFoundException() {
        super("Could not find professional");
    }
}
