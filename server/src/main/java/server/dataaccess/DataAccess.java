package server.dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

/**
 * DataAccess interface
 */
public interface DataAccess {

    UserData getUser(String username) throws DataAccessException;

    void createUser(UserData user) throws DataAccessException;

    void createAuth(AuthData auth) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    Collection<GameData> getGames() throws DataAccessException;

    int createGame(GameData gData) throws DataAccessException;

    void updateGame(GameData game) throws DataAccessException;

    void clear() throws DataAccessException;

}