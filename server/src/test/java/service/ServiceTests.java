package service;

import exception.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.*;
import org.junit.jupiter.api.*;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    private MemoryDataAccess dataAccess;

    @BeforeEach
    public void setup() throws Exception {
        dataAccess = new MemoryDataAccess();
        new ClearService(dataAccess).clear();
    }

    @Test
    public void clearPositive() throws DataAccessException {
        dataAccess.createUser(new UserData("asdf", "asdf", "asdf@gmail.com"));
        dataAccess.clear();
        assertNull(dataAccess.getUser("asdf"));
    }

    @Test
    public void createNewUserPositive() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        AuthData testAuth = new RegisterService(dataAccess).createNewUser(userData);
        assertNotNull(testAuth);
        assertEquals("asdf", testAuth.username());
    }

    @Test
    public void createNewUserNegative() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        assertThrows(exception.AlreadyTakenException.class, () ->
                new RegisterService(dataAccess).createNewUser(userData));
    }

    @Test
    public void loginUserPositive() throws DataAccessException, exception.UnauthorizedException, exception.BadRequestException, exception.AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        assertNotNull(result);
        assertEquals("asdf", result.username());
    }

    @Test
    public void loginUserNegative() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        UserData wrongPassword = new UserData("asdf", "wrongpassword", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        assertThrows(exception.UnauthorizedException.class, () ->
                new LoginService(dataAccess).loginUser(wrongPassword));
    }

    @Test
    public void endSessionPositive() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException, exception.UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        new LogoutService(dataAccess).endSession(result.authToken());
        assertNull(dataAccess.getAuth(result.authToken()));
    }

    @Test
    public void endSessionNegative() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException, exception.UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        new LogoutService(dataAccess).endSession(result.authToken());
        assertThrows(exception.UnauthorizedException.class, () ->
                new LogoutService(dataAccess).endSession("fakeToken"));
    }

    @Test
    public void listGamesPositive() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException, exception.UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        new CreateGameService(dataAccess).newGame(result.authToken(), "game1");
        new CreateGameService(dataAccess).newGame(result.authToken(), "game2");
        Collection<GameData> games = new ListGamesService(dataAccess).listGames(result.authToken());
        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    public void listGamesNegative() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException, exception.UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        new CreateGameService(dataAccess).newGame(result.authToken(), "game1");
        new CreateGameService(dataAccess).newGame(result.authToken(), "game2");
        assertThrows(exception.UnauthorizedException.class, () ->
                new ListGamesService(dataAccess).listGames("fakeToken"));
    }


    @Test
    public void newGamePositive() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException, exception.UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        int gameID = new CreateGameService(dataAccess).newGame(result.authToken(), "testGame");
        assertTrue(gameID > 0);
    }

    @Test
    public void newGameNegative() throws DataAccessException, exception.BadRequestException, exception.AlreadyTakenException, exception.UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        assertThrows(exception.UnauthorizedException.class, () ->
                new CreateGameService(dataAccess).newGame("fakeToken", "testGame"));
    }

    @Test
    public void joinGamePositive() throws DataAccessException, exception.UnauthorizedException, exception.BadRequestException, exception.AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        int gameID = new CreateGameService(dataAccess).newGame(result.authToken(), "testGame");
        new JoinGameService(dataAccess).joinGame(result.authToken(), "WHITE", gameID);
        assertEquals("asdf", dataAccess.getGame(gameID).whiteUsername());
    }

    @Test
    public void joinGameNegative() throws DataAccessException, exception.UnauthorizedException, exception.BadRequestException, exception.AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        int gameID = new CreateGameService(dataAccess).newGame(result.authToken(), "testGame");
        assertThrows(exception.UnauthorizedException.class, () ->
                new JoinGameService(dataAccess).joinGame("fakeToken", "WHITE", gameID));
    }
}