package server.dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryDataAccess implements DataAccess {

    private HashMap<String, UserData> users = new HashMap<>();
    private HashMap<String, AuthData> authMap = new HashMap<>();
    private HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        users.put(user.username(), user);

    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        authMap.put(auth.authToken(), auth);

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return authMap.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        authMap.remove(authToken);
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
    public void updateGame(GameData game) throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {
        games.clear();
        authMap.clear();
        users.clear();
    }
}
