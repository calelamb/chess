package service;

/**
 * Exception thrown when a request is missing required fields or contains invalid data.
 */
public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(String message, Throwable ex) {
        super(message, ex);
    }
}
