package ui;

import java.net.http.HttpClient;

public class ServerFacade {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void registerUser () {

    }

    public void loginUser() {

    }

    public void logoutUser() {

    }

    public void listGames() {

    }

    public void createGame() {

    }

    public void joinGame() {

    }
}
