package client;

import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.DataAccessException;
import exception.UnauthorizedException;
import model.AuthData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.io.IOException;
import java.net.URISyntaxException;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        facade = new ServerFacade("http://localhost:" + port);
        System.out.println("Started test HTTP server on " + port);
    }

    @BeforeEach
    public void clearDatabase() throws UnauthorizedException, BadRequestException, URISyntaxException,
            IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        facade.clearDatabase();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerUserSuccess() throws UnauthorizedException, BadRequestException, URISyntaxException,
            IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        AuthData result = facade.registerUser("testuser", "password", "test@email.com");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("testuser", result.username());
    }

    @Test
    public void registerUserFail() {
        Assertions.assertThrows(AlreadyTakenException.class, () -> {
            facade.registerUser("testuser", "password", "test@email.com");
            facade.registerUser("testuser", "password", "test@email.com");
        });
    }

    @Test
    public void loginUserSuccess() throws UnauthorizedException, BadRequestException, URISyntaxException,
            IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        facade.registerUser("testuser", "password", "test@email.com");
        AuthData result = facade.loginUser("testuser", "password");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("testuser", result.username());
    }

    @Test
    public void loginUserFail() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            facade.registerUser("testuser", "password", "test@email.com");
            facade.loginUser("testuser", "wrongpassword");
        });
    }

    @Test
    public void logoutUserSuccess() throws UnauthorizedException, BadRequestException, URISyntaxException,
            IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        AuthData auth = facade.registerUser("testuser", "password", "test@email.com");
        facade.logoutUser(auth.authToken());
    }

    @Test
    public void logoutUserFail() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            facade.logoutUser("badtoken");
        });
    }

    @Test
    public void listGamesSuccess() throws UnauthorizedException, BadRequestException, URISyntaxException, IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        AuthData auth = facade.registerUser("testuser", "password", "test@email.com");
        facade.createGame(auth.authToken(), "testgame");
        var games = facade.listGames(auth.authToken());
        Assertions.assertNotNull(games);
        Assertions.assertFalse(games.isEmpty());
    }

    @Test
    public void listGamesFail() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            facade.listGames("badtoken");
        });
    }

    @Test
    public void createGameSuccess() throws UnauthorizedException, BadRequestException, URISyntaxException, IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        AuthData auth = facade.registerUser("testuser", "password", "test@email.com");
        int gameID = facade.createGame(auth.authToken(), "testgame");
        Assertions.assertTrue(gameID > 0);
    }

    @Test
    public void createGameFail() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            facade.createGame("badtoken", "testgame");
        });
    }

    @Test
    public void joinGameSuccess() throws UnauthorizedException, BadRequestException, URISyntaxException, IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        AuthData auth = facade.registerUser("testuser", "password", "test@email.com");
        int gameID = facade.createGame(auth.authToken(), "testgame");
        facade.joinGame(auth.authToken(), gameID, "WHITE");
    }

    @Test
    public void joinGameFail() {
        Assertions.assertThrows(AlreadyTakenException.class, () -> {
            AuthData auth = facade.registerUser("testuser", "password", "test@email.com");
            int gameID = facade.createGame(auth.authToken(), "testgame");
            facade.joinGame(auth.authToken(), gameID, "WHITE");
            facade.joinGame(auth.authToken(), gameID, "WHITE");
        });
    }

}
