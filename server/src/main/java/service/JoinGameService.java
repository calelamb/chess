package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;

/**
 * Handles a user joining an existing chess game by verifying their auth token, validating the game and color availability, and updating the game with the new player.
 */
public class JoinGameService {

    private final DataAccess data;

    public JoinGameService(DataAccess d) {
        this.data = d;
    }

    /**
     * Checks the validity of an active game's auth token, availability of a player color, and gameID to handle a user joining an existing game.
     *
     * @param authToken   unique authentication token used to verify the user's session
     * @param playerColor the player color to join as, either WHITE or BLACK
     * @param gameID      the unique identifier of the game to join
     * @throws DataAccessException   thrown if there is an error accessing the data from memory
     * @throws UnauthorizedException thrown if the authorization token could not be retrieved/verified
     * @throws AlreadyTakenException thrown if the desired player color is already taken by another user
     * @throws BadRequestException   thrown if the gameID doesn't exist
     */
    public void joinGame(String authToken, String playerColor, int gameID) throws DataAccessException, UnauthorizedException, AlreadyTakenException, BadRequestException {
        AuthData existingAuth = data.getAuth(authToken);
        GameData existingGame = data.getGame(gameID);
        if (existingAuth != null) {
            if (existingGame != null) {
                if (playerColor.equalsIgnoreCase("WHITE") && existingGame.whiteUsername() != null) {
                    throw new AlreadyTakenException("Player color already taken");
                }
                if (playerColor.equalsIgnoreCase("BLACK") && existingGame.blackUsername() != null) {
                    throw new AlreadyTakenException("Player color already taken");
                }

                switch (playerColor) {
                    case "WHITE":
                        data.updateGame(new GameData(existingGame.gameID(), existingAuth.username(), existingGame.blackUsername(), existingGame.gameName(), existingGame.game()));
                        break;

                    case "BLACK":
                        data.updateGame(new GameData(existingGame.gameID(), existingGame.whiteUsername(), existingAuth.username(), existingGame.gameName(), existingGame.game()));
                        break;

                    default:
                        throw new BadRequestException("Invalid player color");

                }
            } else {
                throw new BadRequestException("Invalid GameID");
            }
        } else {
            throw new UnauthorizedException("Auth token could not be verified");
        }

    }

}
