package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

/**
 * Interface defining the data access operations for the Chess server. Provides methods for storing and retrieving users, games, and authentication tokens.
 */
public interface DataAccess {

    /**
     * Returns the users data
     *
     * @param username the username to search for
     * @return the UserData object if found, null if not
     * @throws DataAccessException thrown if there's an error accessing the data
     */
    UserData getUser(String username) throws DataAccessException;

    /**
     * Creates a UserData object based on user data
     *
     * @param user UserData used to createUser
     * @throws DataAccessException thrown if user cannot be created
     */
    void createUser(UserData user) throws DataAccessException;

    /**
     * Creates a AuthData object with data from the auth param
     *
     * @param auth AuthData used to createAuth
     * @throws DataAccessException thrown if there's an error accessing the data
     */
    void createAuth(AuthData auth) throws DataAccessException;

    /**
     *
     * @param authToken
     * @return
     * @throws DataAccessException
     */
    AuthData getAuth(String authToken) throws DataAccessException;

    /**
     *
     * @param authToken
     * @throws DataAccessException
     */
    void deleteAuth(String authToken) throws DataAccessException;

    /**
     *
     * @return
     * @throws DataAccessException
     */
    Collection<GameData> getGames() throws DataAccessException;

    /**
     *
     * @param gData
     * @return
     * @throws DataAccessException
     */
    int createGame(GameData gData) throws DataAccessException;

    /**
     *
     * @param game
     * @throws DataAccessException
     */
    void updateGame(GameData game) throws DataAccessException;

    /**
     *
     * @throws DataAccessException
     */
    void clear() throws DataAccessException;

}