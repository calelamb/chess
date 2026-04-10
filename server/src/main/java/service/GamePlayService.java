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

import java.util.List;

public class GamePlayService {

    private final DataAccess data;

    public GamePlayService(DataAccess data) {
        this.data = data;
    }

    public record GamePlayResult(List<ServerMessage> toSender, List<ServerMessage> toOthers) {
    }


    public GamePlayResult connect(UserGameCommand command) throws DataAccessException {
        GameData game = data.getGame(command.getGameID());
        AuthData auth = data.getAuth(command.getAuthToken());
        if (auth == null) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: invalid auth")), null);
        }
        if (game == null) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: invalid game ID")), null);
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
        return new GamePlayResult(List.of(new LoadGameMessage(game.game())), List.of(new NotificationMessage(notificationText)));
    }


    public GamePlayResult makeMove(MakeMoveCommand move) throws DataAccessException {
        GameData game = data.getGame(move.getGameID());
        AuthData auth = data.getAuth(move.getAuthToken());
        String role;

        if (auth == null) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: invalid auth")), null);
        }
        if (game == null) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: invalid game ID")), null);
        }

        if (auth.username().equals(game.whiteUsername())) {
            role = "white";
        } else if (auth.username().equals(game.blackUsername())) {
            role = "black";
        } else {
            role = "observer";
            return new GamePlayResult(List.of(new ErrorMessage("Invalid role: Must be a player")), null);
        }

    }


    public GamePlayResult leave(UserGameCommand command) {
        return null;

    }


    public GamePlayResult resign(UserGameCommand command) {
        return null;

    }


}
