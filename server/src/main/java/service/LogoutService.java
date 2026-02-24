package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.UserData;

public class LogoutService {

    private DataAccess data;

    public LogoutService(DataAccess d) {
        this.data = d;
    }

    /**
     *
     */
    public void endSession(String authToken) throws DataAccessException, UnauthorizedException {
        UserData token = data.getUser(authToken);
        if (token != null) {
            data.deleteAuth(authToken);
        } else {
            throw new UnauthorizedException("Auth token could not be verified");
        }
    }
}
