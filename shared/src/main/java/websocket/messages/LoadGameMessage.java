package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {

    private ChessGame game;

    public LoadGameMessage(ChessGame c) {
        super(ServerMessageType.LOAD_GAME);

        this.game = c;
    }
    public ChessGame getGame() {
        return game;
    }

}
