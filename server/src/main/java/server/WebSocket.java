package server;

import model.GameData;
import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;

public class WebSocket {

    private HashMap<Integer, HashMap<Session, String>> sessions = new HashMap<>();

}
