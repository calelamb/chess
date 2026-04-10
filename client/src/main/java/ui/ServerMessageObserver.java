package ui;

import websocket.messages.ServerMessage;

public interface ServerMessageObserver {

    void notifyMessage(String json, ServerMessage message);


}
