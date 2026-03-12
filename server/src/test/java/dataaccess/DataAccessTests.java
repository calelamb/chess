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
}