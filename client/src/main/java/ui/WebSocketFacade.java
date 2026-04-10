package ui;

public class WebSocketFacade {

    private final String serverURL;

    public WebSocketFacade(String serverUrl, ServerMessageObserver observer, String serverURL) {

        this.serverURL = serverURL;
    }
}
