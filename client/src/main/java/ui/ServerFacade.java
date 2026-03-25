package ui;

import com.google.gson.Gson;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.DataAccessException;
import exception.UnauthorizedException;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ServerFacade {

    private final Gson GSON = new Gson();
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private <T> T makeRequest(String method, String path, String authToken, Class<T> responseClass, Object body) throws URISyntaxException, IOException, InterruptedException, BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
        String fullUrl = serverUrl + path;
        String jsonBody;

        if (body != null) {
            jsonBody = GSON.toJson(body);
        } else {
            jsonBody = "";
        }

        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(new URI(fullUrl));
        builder = builder.header("Content-Type", "application/json");
        if (authToken != null) {
            builder = builder.header("Authorization", authToken);
        }
        builder = builder.method(method, HttpRequest.BodyPublishers.ofString(jsonBody));
        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int status = response.statusCode();
        switch (status) {
            case 400:
                throw new BadRequestException("Bad request");
                break;

            case 401:
                throw new UnauthorizedException("Unauthorized");
                break;

            case 403:
                throw new AlreadyTakenException("Already taken");
                break;

            case 500:
                throw new DataAccessException("Error accessing the database");
                break;

            default:

        }

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

    public int createGame(String authToken, String gameName) {

    }

    public void joinGame(String authToken, int gameID, String teamColor) {

    }
}
