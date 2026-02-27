package server;

/**
 * Represents an error message response to be serialized as JSON.
 *
 * @param message
 */
public record ErrorMessage(String message) {
}
