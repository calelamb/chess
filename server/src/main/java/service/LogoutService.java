package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;

public class LogoutService {

    private DataAccess data;

    public LogoutService(DataAccess d) {
        this.data = d;
    }

    /**
     *
     */
    public void endSession(String authToken) throws DataAccessException, UnauthorizedException {
        AuthData token = data.getAuth(authToken);
        if (token != null) {
            data.deleteAuth(authToken);
        } else {
            throw new UnauthorizedException("Auth token could not be verified");
        }
    }
}
