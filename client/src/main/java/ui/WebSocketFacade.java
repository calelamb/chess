package ui;

import chess.ChessMove;
import com.google.gson.Gson;
import jakarta.websocket.*;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.net.URI;

public class WebSocketFacade extends Endpoint {

    private final String serverURL;
    private final ServerMessageObserver obs;
    private Session s;

    public WebSocketFacade(String url, ServerMessageObserver obs) {

        this.serverURL = url;
        this.obs = obs;
    }


    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        s = session;
        s.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String msg) {
                obs.notifyMessage(msg, ServerMessage.jsonToMessage(msg));

            }
        });

    }

    public void connect() throws Exception {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(serverURL));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void sendConnect(String authToken, int gameID) throws Exception {
        var cmd = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
        s.getBasicRemote().sendText(new Gson().toJson(cmd));
    }

    public void sendLeave(String authToken, int gameID) throws Exception {
        var cmd = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
        s.getBasicRemote().sendText(new Gson().toJson(cmd));
    }

    public void sendResign(String authToken, int gameID) throws Exception {
        var cmd = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID);
        s.getBasicRemote().sendText(new Gson().toJson(cmd));
    }

    public void sendMakeMove(String authToken, int gameID, ChessMove move) throws Exception {

        var cmd = new MakeMoveCommand(authToken, gameID, move);
        s.getBasicRemote().sendText(new Gson().toJson(cmd));
    }
}
