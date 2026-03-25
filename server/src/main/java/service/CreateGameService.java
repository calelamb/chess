package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import exception.DataAccessException;
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
     * @throws exception.UnauthorizedException thrown if there's an error verifying the auth token
     * @throws DataAccessException   thrown if there's an error accessing the game data
     */
    public int newGame(String authToken, String gameName) throws exception.UnauthorizedException, DataAccessException, exception.BadRequestException {
        if (gameName == null || gameName.isEmpty()) {
            throw new exception.BadRequestException("Missing game name");
        } else {
            AuthData existingAuth = data.getAuth(authToken);
            if (existingAuth != null) {
                return data.createGame(new GameData(0, null, null, gameName, new ChessGame()));
            } else {
                throw new exception.UnauthorizedException("Auth token could not be verified");
            }
        }
    }
}
