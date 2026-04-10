package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    private ChessMove chessMove;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move) {
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.chessMove = move;
    }

    public ChessMove getChessMove() {
        return chessMove;
    }

}
