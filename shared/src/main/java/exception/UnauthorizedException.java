package exception;

/**
 * Exception thrown when a request contains invalid or missing auth token
 */
public class UnauthorizedException extends Exception {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable ex) {
        super(message, ex);
    }
}
