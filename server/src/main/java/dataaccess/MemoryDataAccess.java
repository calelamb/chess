package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collection;
import java.util.HashMap;

/**
 * MemoryDataAccess class that inherits methods from the DataAccess interface, used to manipulate User, Game, and Auth data in a chess game.
 * Stores data in memory using the built-in HashMap structure for use in the Chess server.
 *
 */
public class MemoryDataAccess implements DataAccess {

    private HashMap<String, UserData> users = new HashMap<>();
    private HashMap<String, AuthData> authMap = new HashMap<>();
    private HashMap<Integer, GameData> games = new HashMap<>();
    private int uniqueID = 1;

    @Override
    public UserData getUser(String username) throws exception.DataAccessException {
        return users.get(username);
    }

    @Override
    public void createUser(UserData user) throws exception.DataAccessException {
        String hashed = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        users.put(user.username(), new UserData(user.username(), hashed, user.email()));


    }

    @Override
    public void createAuth(AuthData auth) throws exception.DataAccessException {
        authMap.put(auth.authToken(), auth);

    }

    @Override
    public AuthData getAuth(String authToken) throws exception.DataAccessException {
        return authMap.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws exception.DataAccessException {
        authMap.remove(authToken);
    }

    @Override
    public Collection<GameData> getGames() throws exception.DataAccessException {
        return games.values();
    }

    @Override
    public int createGame(GameData gData) throws exception.DataAccessException {
        games.put(uniqueID, new GameData(uniqueID, gData.whiteUsername(), gData.blackUsername(), gData.gameName(), gData.game()));
        return uniqueID++;
    }

    @Override
    public GameData getGame(int gID) throws exception.DataAccessException {
        return games.get(gID);
    }

    @Override
    public void updateGame(GameData game) throws exception.DataAccessException {
        games.put(game.gameID(), game);

    }

    @Override
    public void clear() throws exception.DataAccessException {
        games.clear();
        authMap.clear();
        users.clear();
    }
}
