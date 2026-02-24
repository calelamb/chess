package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

/**
 * LoginService class that handles user login by verifying credentials and generating a new authentication token.
 */
public class LoginService {

    private final DataAccess data;

    public LoginService(DataAccess d) {
        this.data = d;
    }


    /**
     * Takes in a userdata object, verifies the username and password,
     * then logs the user in and creates a unique session ID.
     *
     * @param d a UserData object
     * @return a unique session ID generated after login
     * @throws UnauthorizedException thrown if there is an error matching username, or password to username
     * @throws DataAccessException   thrown if there is an error accessing the UserData
     */
    public AuthData loginUser(UserData d) throws UnauthorizedException, DataAccessException {
        String username = d.username();
        UserData existingUser = data.getUser(username);
        if (existingUser != null) {
            if (d.password().equals(existingUser.password())) {

                String token = UUID.randomUUID().toString();
                AuthData authData = new AuthData(d.username(), token);
                data.createAuth(authData);

                return authData;
            } else {
                throw new UnauthorizedException("Incorrect password");
            }
        } else {
            throw new UnauthorizedException("No user found with that username");
        }
    }
}
