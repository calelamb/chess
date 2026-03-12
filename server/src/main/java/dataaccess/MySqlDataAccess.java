package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;


public class MySqlDataAccess implements DataAccess {
    public MySqlDataAccess() throws DataAccessException {
        DatabaseManager.createDatabase();
        configureDatabase();
    }

    private void configureDatabase() throws DataAccessException {
        String[] statements = {
                """
            CREATE TABLE IF NOT EXISTS users (
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email    VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
            )
            """,
                """
            CREATE TABLE IF NOT EXISTS auth (
                authToken VARCHAR(255) NOT NULL,
                username  VARCHAR(255) NOT NULL,
                PRIMARY KEY (authToken)
            )
            """,
                """
            CREATE TABLE IF NOT EXISTS games (
                gameID        INT NOT NULL AUTO_INCREMENT,
                whiteUsername VARCHAR(255),
                blackUsername VARCHAR(255),
                gameName      VARCHAR(255) NOT NULL,
                game          TEXT NOT NULL,
                PRIMARY KEY (gameID)
            )
            """
        };
        try (var conn = DatabaseManager.getConnection()) {
            for (String statement : statements) {
                try (var ps = conn.prepareStatement(statement)) {
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to configure database: " + e.getMessage());
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        String sql = "SELECT username, password, email FROM users WHERE username = ?";
        try (var conn = DatabaseManager.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error getting user: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        String hashed = BCrypt.hashpw(user.password(), BCrypt.gensalt());
    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public Collection<GameData> getGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public int createGame(GameData gData) throws DataAccessException {
        return 0;
    }

    @Override
    public GameData getGame(int gID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {

    }
}
