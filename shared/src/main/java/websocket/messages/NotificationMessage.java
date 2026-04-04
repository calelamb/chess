package websocket.messages;

public class NotificationMessage extends ServerMessage {

    private String message;
    public NotificationMessage(String s) {
        super(ServerMessageType.NOTIFICATION);

        this.message = s;
    }
}
