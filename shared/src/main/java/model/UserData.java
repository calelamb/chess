package model;


/**
 * UserData record that represents a UserData object by their username, password, and email
 *
 * @param username
 * @param password
 * @param email
 */
public record UserData(String username, String password, String email) {
}
