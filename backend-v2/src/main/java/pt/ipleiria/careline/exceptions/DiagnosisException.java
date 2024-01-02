package pt.ipleiria.careline.exceptions;

public class DiagnosisException extends RuntimeException {
    public DiagnosisException(Long id) {
        super("Could not find diagnosis with id: " + id);
    }

    public DiagnosisException() {
        super("Could not find diagnosis");
    }

    public DiagnosisException(String message) {
        super(message);
    }
}
