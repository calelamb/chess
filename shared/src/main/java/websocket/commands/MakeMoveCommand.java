package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    private ChessMove move;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove m) {
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.move = m;
    }

    public ChessMove getChessMove() {
        return move;
    }

}
