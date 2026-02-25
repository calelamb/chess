package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;

public class JoinGameService {

    private final DataAccess data;

    public JoinGameService(DataAccess d) {
        this.data = d;
    }

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
