package pt.ipleiria.careline.exceptions;

public class TemperatureException extends RuntimeException {

    public TemperatureException(String message) {
        super(message);
    }

    public TemperatureException() {
        super("Could not find temperature");
    }
}
