package websocket.messages;

import chess.ChessGame;

public class ErrorMessage extends ServerMessage{

    private String errorMessage;
    public ErrorMessage(String s) {
        super(ServerMessageType.ERROR);

        this.errorMessage = s;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
