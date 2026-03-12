package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DataAccessTests {

    private static MySqlDataAccess dao;

    @BeforeEach
    void setup() throws Exception {
        dao = new MySqlDataAccess();
        dao.clear();
    }

    @Test
    void createUserPositive() throws Exception {
        dao.createUser(new UserData("cale", "password", "cale@gmail.com"));
        assertNotNull(dao.getUser("cale"));
    }

    @Test
    void createUserNegative() throws Exception {
        dao.createUser(new UserData("cale", "password", "cale@gmail.com"));
        assertThrows(DataAccessException.class, () ->
                dao.createUser(new UserData("cale", "password", "cale@gmail.com")));
    }

    @Test
    void getUserPositive() throws Exception {
        dao.createUser(new UserData("cale", "password", "cale@gmail.com"));
        UserData userTest = dao.getUser("cale");
        assertEquals("cale", userTest.username());
    }

    @Test
    void getUserNegative() throws Exception {
        assertNull(dao.getUser("should be null"));
    }

    @Test
    void createAuthPositive() throws Exception {
        dao.createAuth(new AuthData("cale", "token123"));
        assertNotNull(dao.getAuth("token123"));
    }

    @Test
    void createAuthNegative() throws Exception {
        dao.createAuth(new AuthData("cale", "token123"));
        assertThrows(DataAccessException.class, () ->
                dao.createAuth(new AuthData("cale", "token123")));
    }

    @Test
    void getAuthPositive() throws Exception {
        dao.createAuth(new AuthData("cale", "token123"));
        AuthData auth = dao.getAuth("token123");
        assertEquals("cale", auth.username());
    }

    @Test
    void getAuthNegative() throws Exception {
        assertNull(dao.getAuth("faketoken"));
    }

    @Test
    void deleteAuthPositive() throws Exception {
        dao.createAuth(new AuthData("cale", "token123"));
        dao.deleteAuth("token123");
        assertNull(dao.getAuth("token123"));
    }

    @Test
    void deleteAuthNegative() throws Exception {
        assertDoesNotThrow(() -> dao.deleteAuth("nonexistenttoken"));
    }

    @Test
    void createGamePositive() throws Exception {
        int id = dao.createGame(new GameData(0, null, null, "testGame", new ChessGame()));
        assertTrue(id > 0);
    }

    @Test
    void createGameNegative() throws Exception {
        assertThrows(DataAccessException.class, () ->
                dao.createGame(new GameData(0, null, null, null, new ChessGame())));
    }

    @Test
    void getGamePositive() throws Exception {
        int id = dao.createGame(new GameData(0, null, null, "testGame", new ChessGame()));
        GameData game = dao.getGame(id);
        assertEquals("testGame", game.gameName());
    }

    @Test
    void getGameNegative() throws Exception {
        assertNull(dao.getGame(99999));
    }

    @Test
    void getGamesPositive() throws Exception {
        dao.createGame(new GameData(0, null, null, "game1", new ChessGame()));
        dao.createGame(new GameData(0, null, null, "game2", new ChessGame()));
        assertEquals(2, dao.getGames().size());
    }

    @Test
    void getGamesNegative() throws Exception {
        assertNotNull(dao.getGames());
        assertEquals(0, dao.getGames().size());
    }

    @Test
    void updateGamePositive() throws Exception {
        int id = dao.createGame(new GameData(0, null, null, "testGame", new ChessGame()));
        dao.updateGame(new GameData(id, "cale", null, "testGame", new ChessGame()));
        assertEquals("cale", dao.getGame(id).whiteUsername());
    }

    @Test
    void updateGameNegative() throws Exception {
        assertDoesNotThrow(() ->
                dao.updateGame(new GameData(99999, "cale", null, "testGame", new ChessGame())));
    }

    @Test
    void clearPositive() throws Exception {
        dao.createUser(new UserData("cale", "password", "cale@gmail.com"));
        dao.createGame(new GameData(0, null, null, "testGame", new ChessGame()));
        dao.clear();
        assertNull(dao.getUser("cale"));
        assertEquals(0, dao.getGames().size());
    }


}