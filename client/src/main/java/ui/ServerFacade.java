package ui;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.net.http.HttpClient;
import java.util.List;

public class ServerFacade {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public AuthData registerUser(String username, String password, String email) {
        UserData ud = new UserData(username, password, email);
    }

    public AuthData loginUser(String username, String password) {

    }

    public void logoutUser(String authToken) {

    }

    public List<GameData> listGames(String authToken) {

    }

    public GameData createGame(String authToken, String gameName) {

    }

    public void joinGame(String authToken, int gameID, String teamColor) {

    }
}
