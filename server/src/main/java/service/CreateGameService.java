package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;

/**
 *
 */
public class CreateGameService {

    private final DataAccess data;

    public CreateGameService(DataAccess d) {
        this.data = d;
    }

    /**
     * Verifies the auth token, creates a new Chess game with the given name, and returns the unique game ID.
     *
     * @param authToken Unique authentication token used to verify the user's session
     * @param gameName
     * @return
     * @throws UnauthorizedException
     * @throws DataAccessException
     */
    public int newGame(String authToken, String gameName) throws UnauthorizedException, DataAccessException {
        AuthData existingAuth = data.getAuth(authToken);
        if (existingAuth != null) {
            int gID = data.createGame(new GameData(0, null, null, gameName, new ChessGame()));
            return gID;
        } else {
            throw new UnauthorizedException("Auth token could not be verified");
        }

    }
}
