package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

/**
 * Class for the RegisterService endpoint. Handles the registration of a new user, and their auth tokens.
 */
public class RegisterService {

    private final DataAccess data;

    public RegisterService(DataAccess d) {
        this.data = d;
    }

    /**
     * Handles the creation of a new user and generates a unique identifier, then returns the AuthData
     *
     * @param d UserData object used to create new user
     * @return AuthData (unique identifier and session token) of created user
     * @throws BadRequestException   thrown if bad or missing data is passed
     * @throws AlreadyTakenException thrown if desired username is already found in the database
     * @throws DataAccessException   thrown if there is an error creating the user
     */
    public AuthData createNewUser(UserData d) throws BadRequestException, AlreadyTakenException, DataAccessException {
        if (d.username() == null || d.password() == null || d.email() == null) {
            throw new BadRequestException("One of more fields is empty");
        } else if (d.username().isEmpty() || d.password().isEmpty() || d.email().isEmpty()) {
            throw new BadRequestException("One or more fields has empty space");
        }
        if (data.getUser(d.username()) != null) {
            throw new AlreadyTakenException("Username already taken");
        }

        data.createUser(d);
        String token = UUID.randomUUID().toString();
        AuthData authData = new AuthData(d.username(), token);
        data.createAuth(authData);

        return authData;
    }


}
