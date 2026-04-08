package server;

import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;

import java.util.HashMap;

public class WebSocket {

    private HashMap<Integer, HashMap<Session, String>> sessions = new HashMap<>();

    private void handleMessage(Session session, String jsonInput) {
        UserGameCommand command = UserGameCommand.jsonToCommand(jsonInput);
        UserGameCommand.CommandType commandType = command.getCommandType();

        switch(commandType) {

            case(UserGameCommand.CommandType.CONNECT) -> {

            }

            case(UserGameCommand.CommandType.MAKE_MOVE) -> {

            }

            case(UserGameCommand.CommandType.LEAVE) -> {

            }

            case(UserGameCommand.CommandType.RESIGN) -> {

            }
        }
    }
}
