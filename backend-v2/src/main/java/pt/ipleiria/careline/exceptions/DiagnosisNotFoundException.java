package pt.ipleiria.careline.exceptions;

public class DiagnosisNotFoundException extends RuntimeException {
    public DiagnosisNotFoundException(Long id) {
        super("Could not find diagnosis with id: " + id);
    }

    public DiagnosisNotFoundException() {
        super("Could not find diagnosis");
    }
}
