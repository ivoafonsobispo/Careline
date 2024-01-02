package pt.ipleiria.careline.exceptions;

public class HeartbeatException extends RuntimeException {
    public HeartbeatException(String nus) {
        super("Could not find patient with nus: " + nus);
    }

    public HeartbeatException(Long id) {
        super("Could not find patient with id: " + id);
    }

    public HeartbeatException(String email, String password) {
        super("Could not find patient with email: " + email + " and password: " + password);
    }

    public HeartbeatException() {
        super("Could not find patient");
    }
}
