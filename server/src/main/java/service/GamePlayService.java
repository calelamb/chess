package service;

import chess.ChessGame;
import chess.InvalidMoveException;
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

import java.util.ArrayList;
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
        String role = "";

        if (auth == null) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: invalid auth")), null);
        }
        if (game == null) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: invalid game ID")), null);
        }

        ChessGame chessGame = game.game();
        if (chessGame.isGameOver()) {
            return new GamePlayResult(List.of(new ErrorMessage("game is over")), null);
        }

        if (auth.username().equals(game.whiteUsername())) {
            role = "white";
        } else if (auth.username().equals(game.blackUsername())) {
            role = "black";
        } else {
            role = "observer";
        }
        if (role.equals("observer")) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: observers cannot move")), null);
        }
        ChessGame.TeamColor senderColor = role.equals("white") ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;

        if (chessGame.getTeamTurn() != senderColor) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: not your turn")), null);
        }

        try {
            chessGame.makeMove(move.getChessMove());
            NotificationMessage stateNotification = null;
        } catch (InvalidMoveException e) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: invalid move")), null);
        }

        ChessGame.TeamColor opponentColor = chessGame.getTeamTurn();
        String opponentUsername = opponentColor == ChessGame.TeamColor.WHITE
                ? game.whiteUsername() : game.blackUsername();

        NotificationMessage stateNotification = null;
        if (chessGame.isInCheckmate(opponentColor)) {
            chessGame.setGameOver(true);
            stateNotification = new NotificationMessage(opponentUsername + " is in checkmate");
        } else if (chessGame.isInStalemate(opponentColor)) {
            chessGame.setGameOver(true);
            stateNotification = new NotificationMessage("Stalemate");
        } else if (chessGame.isInCheck(opponentColor)) {
            stateNotification = new NotificationMessage(opponentUsername + " is in check");
        }

        data.updateGame(game);

        String moveDesc = auth.username() + " moved " + move.getChessMove().toString();

        List<ServerMessage> toSender = new ArrayList<>();
        toSender.add(new LoadGameMessage(chessGame));

        List<ServerMessage> toOthers = new ArrayList<>();
        toOthers.add(new LoadGameMessage(chessGame));
        toOthers.add(new NotificationMessage(moveDesc));

        if (stateNotification != null) {
            toSender.add(stateNotification);
            toOthers.add(stateNotification);
        }

        return new GamePlayResult(toSender, toOthers);

    }


    public GamePlayResult leave(UserGameCommand command) throws DataAccessException {
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

        if (!role.equals("observer")) {
            GameData updated = new GameData(
                    game.gameID(),
                    role.equals("white") ? null : game.whiteUsername(),
                    role.equals("black") ? null : game.blackUsername(),
                    game.gameName(),
                    game.game()
            );
            data.updateGame(updated);
        }

        String leaveText = auth.username() + " left the game";
        return new GamePlayResult(List.of(), List.of(new NotificationMessage(leaveText)));
    }


    public GamePlayResult resign(UserGameCommand command) throws DataAccessException {
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
            return new GamePlayResult(List.of(new ErrorMessage("Error: observers cannot resign")), null);
        }

        ChessGame chessGame = game.game();
        if (chessGame.isGameOver()) {
            return new GamePlayResult(List.of(new ErrorMessage("Error: game is already over")), null);
        }

        chessGame.setGameOver(true);
        data.updateGame(game);

        String resignText = auth.username() + " resigned";
        NotificationMessage notification = new NotificationMessage(resignText);
        return new GamePlayResult(List.of(notification), List.of(notification));
    }

}
