package ui;

import jakarta.websocket.*;
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
}
