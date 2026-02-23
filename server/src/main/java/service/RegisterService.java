package service;

import dataaccess.DataAccess;
import model.AuthData;
import model.UserData;

public class RegisterService {

    private DataAccess data;

    public RegisterService(DataAccess d) {
        this.data = d;
    }

    public AuthData createNewUser(UserData d) throws {
        return new AuthData(d.username(), );
    }

}
