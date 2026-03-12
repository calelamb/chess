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

}