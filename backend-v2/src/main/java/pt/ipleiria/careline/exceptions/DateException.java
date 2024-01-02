package pt.ipleiria.careline.exceptions;

public class DateException extends RuntimeException {
    public DateException() {
        super("Invalid date");
    }
}
