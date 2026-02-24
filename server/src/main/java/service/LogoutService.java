package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;

/**
 * Logout Service endpoint class. Handles ending a user's session
 */
public class LogoutService {

    private final DataAccess data;

    public LogoutService(DataAccess d) {
        this.data = d;
    }

    /**
     * Ends a specified game session identified by auth/session token. Verifies token first,
     * then deletes from store.
     *
     * @param authToken Unique authentication token used to verify the user's session
     * @throws DataAccessException   thrown if there is an error fetching the auth token
     * @throws UnauthorizedException thrown if auth token given cannot be verified in the data store.
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
