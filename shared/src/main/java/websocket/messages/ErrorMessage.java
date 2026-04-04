package websocket.messages;

public class ErrorMessage extends ServerMessage{

    private String errorMessage;
    public ErrorMessage(String s) {
        super(ServerMessageType.ERROR);

        this.errorMessage = s;
    }
}
