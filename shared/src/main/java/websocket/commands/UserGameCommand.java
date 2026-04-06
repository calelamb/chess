package websocket.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * <p>
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    private final CommandType commandType;

    private final String authToken;

    private final Integer gameID;

    private static Gson GSON = new Gson();

    public UserGameCommand(CommandType commandType, String authToken, Integer gameID) {
        this.commandType = commandType;
        this.authToken = authToken;
        this.gameID = gameID;
    }

    public enum CommandType {
        CONNECT,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Integer getGameID() {
        return gameID;
    }

    public static UserGameCommand jsonToCommand(String jsonInput) throws IllegalArgumentException {
        JsonObject json = JsonParser.parseString(jsonInput).getAsJsonObject();
        String commandType = json.get("commandType").getAsString();

        switch (commandType) {
            case ("CONNECT") -> {
                return GSON.fromJson(jsonInput, UserGameCommand.class);
            }

            case ("LEAVE") -> {
                return GSON.fromJson(jsonInput, UserGameCommand.class);
            }

            case ("RESIGN") -> {
                return GSON.fromJson(jsonInput, UserGameCommand.class);
            }

            case ("MAKE_MOVE") -> {
                return GSON.fromJson(jsonInput, MakeMoveCommand.class);
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
        if (!(o instanceof UserGameCommand that)) {
            return false;
        }
        return getCommandType() == that.getCommandType() &&
                Objects.equals(getAuthToken(), that.getAuthToken()) &&
                Objects.equals(getGameID(), that.getGameID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthToken(), getGameID());
    }
}
