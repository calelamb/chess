package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;

import java.util.Collection;

/**
 * ListGames endpoint class. Handles the retrieving and listing of all active games under an auth token.
 */
public class ListGamesService {

    private final DataAccess data;

    public ListGamesService(DataAccess d) {
        this.data = d;
    }

    /**
     * Takes in an auth token and returns a Collection of the active games associated with that token.
     *
     * @param authToken Unique authentication token used to verify the user's session
     * @return games under that session
     * @throws UnauthorizedException thrown if auth token is invalid or null
     * @throws DataAccessException   thrown if there is an error accessing the data from memory
     */
    public Collection<GameData> listGames(String authToken) throws UnauthorizedException, DataAccessException {
        AuthData token = data.getAuth(authToken);
        if (token != null) {
            return data.getGames();
        } else {
            throw new UnauthorizedException("Auth token could not be verified");
        }
    }
}
