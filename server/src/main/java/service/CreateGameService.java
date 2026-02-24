package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;

/**
 * Handles the creation of a new Chess game after verifying the user's authentication token
 */
public class CreateGameService {

    private final DataAccess data;

    public CreateGameService(DataAccess d) {
        this.data = d;
    }

    /**
     * Verifies the auth token, creates a new Chess game with the given name, and returns the unique game ID.
     *
     * @param authToken unique authentication token used to verify the user's session
     * @param gameName  name of the newly created game
     * @return the gameID of the newly created game
     * @throws UnauthorizedException thrown if there's an error verifying the auth token
     * @throws DataAccessException   thrown if there's an error accessing the game data
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
