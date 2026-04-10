package service;

import dataaccess.DataAccess;
import exception.DataAccessException;
import model.AuthData;
import model.GameData;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

public class GamePlayService {

    private final DataAccess data;

    public GamePlayService(DataAccess data) {
        this.data = data;
    }

    public record GamePlayResult(ServerMessage toSender, ServerMessage toOthers) {
    }


    public GamePlayResult connect(UserGameCommand command) throws DataAccessException {
        GameData game = data.getGame(command.getGameID());
        AuthData auth = data.getAuth(command.getAuthToken());
        if (auth == null) {
            return new GamePlayResult(new ErrorMessage("Error: invalid auth"), null);
        }
        if (game == null) {
            return new GamePlayResult(new ErrorMessage("Error: invalid game ID"), null);
        }
        String role;

        if (auth.username().equals(game.whiteUsername())) {
            role = "white";
        } else if (auth.username().equals(game.blackUsername())) {
            role = "black";
        } else {
            role = "observer";
        }
        String notificationText = auth.username() + " connected as " + role;
        return new GamePlayResult(new LoadGameMessage(game.game()), new NotificationMessage(notificationText));
    }


    public GamePlayResult makeMove(MakeMoveCommand move) throws DataAccessException {
        GameData game = data.getGame(move.getGameID());
        AuthData auth = data.getAuth(move.getAuthToken());
        if (auth == null) {
            return new GamePlayResult(new ErrorMessage("Error: invalid auth"), null);
        }
        if (game == null) {
            return new GamePlayResult(new ErrorMessage("Error: invalid game ID"), null);
        }

    }


    public GamePlayResult leave(UserGameCommand command) {
        return null;

    }


    public GamePlayResult resign(UserGameCommand command) {
        return null;

    }


}
