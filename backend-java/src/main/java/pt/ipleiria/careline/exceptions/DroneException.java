package pt.ipleiria.careline.exceptions;

public class DroneException extends RuntimeException {
    public DroneException() {
        super("Could not find drone");
    }

    public DroneException(String message) {
        super(message);
    }

    public DroneException(Long id) {
        super("Could not find diagnosis with id: " + id);
    }
}
