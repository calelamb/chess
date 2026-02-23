package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class RegisterService {

    private DataAccess data;

    public RegisterService(DataAccess d) {
        this.data = d;
    }

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
