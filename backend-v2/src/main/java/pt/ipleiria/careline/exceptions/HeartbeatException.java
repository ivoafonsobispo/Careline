package pt.ipleiria.careline.exceptions;

public class HeartbeatException extends RuntimeException {

    public HeartbeatException(String message) {
        super(message);
    }

    public HeartbeatException() {
        super("Could not find heartbeat");
    }
}
