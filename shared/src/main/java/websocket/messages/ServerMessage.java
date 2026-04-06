package websocket.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;

/**
 * Represents a Message the server can send through a WebSocket
 * <p>
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class ServerMessage {

    ServerMessageType serverMessageType;

    private static final Gson GSON = new Gson();


    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessage(ServerMessageType type) {
        this.serverMessageType = type;
    }

    public ServerMessageType getServerMessageType() {
        return this.serverMessageType;
    }

    public static ServerMessage jsonToMessage(String jsonInput) {
        JsonObject json = JsonParser.parseString(jsonInput).getAsJsonObject();
        String serverMessageType = json.get("serverMessageType").getAsString();

        switch (serverMessageType) {

            case ("LOAD_GAME") -> {
                return GSON.fromJson(jsonInput, LoadGameMessage.class);
            }

            case ("ERROR") -> {
                return GSON.fromJson(jsonInput, ErrorMessage.class);
            }

            case ("NOTIFICATION") -> {
                return GSON.fromJson(jsonInput, NotificationMessage.class);
            }

            default -> {
                throw new IllegalArgumentException("Invalid Command Type");
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerMessage that)) {
            return false;
        }
        return getServerMessageType() == that.getServerMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerMessageType());
    }
}
