package service;

import dataaccess.DataAccessException;
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
    public void createNewUserPositive() throws DataAccessException, BadRequestException, AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        AuthData testAuth = new RegisterService(dataAccess).createNewUser(userData);
        assertNotNull(testAuth);
        assertEquals("asdf", testAuth.username());
    }

    @Test
    public void createNewUserNegative() throws DataAccessException, BadRequestException, AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        assertThrows(AlreadyTakenException.class, () ->
                new RegisterService(dataAccess).createNewUser(userData));
    }

    @Test
    public void loginUserPositive() throws DataAccessException, UnauthorizedException, BadRequestException, AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        assertNotNull(result);
        assertEquals("asdf", result.username());
    }

    @Test
    public void loginUserNegative() throws DataAccessException, BadRequestException, AlreadyTakenException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        UserData wrongPassword = new UserData("asdf", "wrongpassword", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        assertThrows(UnauthorizedException.class, () ->
                new LoginService(dataAccess).loginUser(wrongPassword));
    }

    @Test
    public void endSessionPositive() throws DataAccessException, BadRequestException, AlreadyTakenException, UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        new LogoutService(dataAccess).endSession(result.authToken());
        assertNull(dataAccess.getAuth(result.authToken()));
    }

    @Test
    public void endSessionNegative() throws DataAccessException, BadRequestException, AlreadyTakenException, UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        new LogoutService(dataAccess).endSession(result.authToken());
        assertThrows(UnauthorizedException.class, () ->
                new LogoutService(dataAccess).endSession("fakeToken"));
    }

    @Test
    public void listGamesPositive() throws DataAccessException, BadRequestException, AlreadyTakenException, UnauthorizedException {
        UserData userData = new UserData("asdf", "asdf", "asdf@gmail.com");
        new RegisterService(dataAccess).createNewUser(userData);
        AuthData result = new LoginService(dataAccess).loginUser(userData);
        new CreateGameService(dataAccess).newGame(result.authToken(), "game1");
        new CreateGameService(dataAccess).newGame(result.authToken(), "game2");
        Collection<GameData> games = new ListGamesService(dataAccess).listGames(result.authToken());
        assertNotNull(games);
        assertEquals(2, games.size());
    }

}