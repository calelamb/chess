package model;

/**
 * AuthData record that represents authorization data that links a user to their unique session token.
 *
 * @param username
 * @param authToken
 */
public record AuthData(String username, String authToken) {
}
