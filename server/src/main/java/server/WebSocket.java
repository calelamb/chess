package server;

import com.google.gson.Gson;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import service.GamePlayService;
import service.GamePlayService.GamePlayResult;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSocket {

    private static final Gson GSON = new Gson();
    private final GamePlayService service;
    private final Map<Integer, Map<Session, String>> sessions = new HashMap<>();

    public WebSocket(GamePlayService service) {
        this.service = service;
    }

    public void handleMessage(Session session, String jsonInput) throws Exception {
        UserGameCommand command = UserGameCommand.jsonToCommand(jsonInput);
        Integer gameID = command.getGameID();
        String authToken = command.getAuthToken();

        GamePlayResult result;
        switch (command.getCommandType()) {
            case CONNECT -> {
                addSession(gameID, session, authToken);
                result = service.connect(command);
            }
            case MAKE_MOVE -> {
                MakeMoveCommand moveCommand = (MakeMoveCommand) UserGameCommand.jsonToCommand(jsonInput);
                result = service.makeMove(moveCommand);
            }
            case LEAVE -> {
                result = service.leave(command);
                removeSession(gameID, session);
            }
            case RESIGN -> {
                result = service.resign(command);
            }
            default -> {
                return;
            }
        }

        broadcast(gameID, session, result);
    }

    private void addSession(Integer gameID, Session session, String authToken) {
        sessions.computeIfAbsent(gameID, k -> new HashMap<>()).put(session, authToken);
    }

    private void removeSession(Integer gameID, Session session) {
        Map<Session, String> gameSessions = sessions.get(gameID);
        if (gameSessions != null) {
            gameSessions.remove(session);
        }
    }

    private void broadcast(Integer gameID, Session sender, GamePlayResult result) throws IOException {
        if (result == null) return;

        if (result.toSender() != null) {
            for (ServerMessage msg : result.toSender()) {
                sender.getRemote().sendString(GSON.toJson(msg));
            }
        }

        if (result.toOthers() != null) {
            Map<Session, String> gameSessions = sessions.get(gameID);
            if (gameSessions != null) {
                for (Session s : gameSessions.keySet()) {
                    if (s != sender && s.isOpen()) {
                        for (ServerMessage msg : result.toOthers()) {
                            s.getRemote().sendString(GSON.toJson(msg));
                        }
                    }
                }
            }
        }
    }
}