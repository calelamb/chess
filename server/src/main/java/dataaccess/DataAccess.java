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
     * Stores a UserData object based on user data
     *
     * @param user UserData used to createUser
     * @throws DataAccessException thrown if user cannot be created
     */
    void createUser(UserData user) throws DataAccessException;

    /**
     * Creates a AuthData object with data from the auth param
     *
     * @param auth AuthData used to createAuth
     * @throws DataAccessException thrown if there's an error creating the auth
     */
    void createAuth(AuthData auth) throws DataAccessException;

    /**
     * Returns an AuthData object, found by the authToken
     *
     * @param authToken a unique session token
     * @return an AuthData object
     * @throws DataAccessException thrown if there's an issue accessing the data
     */
    AuthData getAuth(String authToken) throws DataAccessException;

    /**
     * Deletes an AuthData object
     *
     * @param authToken a unique session token
     * @throws DataAccessException thrown if there's an issue deleting the object
     */
    void deleteAuth(String authToken) throws DataAccessException;

    /**
     * Returns a Collection of all active chess games
     *
     * @return a Collection of games
     * @throws DataAccessException thrown if there's an issue retrieving games
     */
    Collection<GameData> getGames() throws DataAccessException;

    /**
     * Creates a new Chess game with unique game data
     *
     * @param gData GameData record containing the unique data for the game
     * @return the GameID
     * @throws DataAccessException thrown if there's an error creating the game
     */
    int createGame(GameData gData) throws DataAccessException;

    /**
     * Replaces an existing game with updated game data
     *
     * @param game the existing GameData object
     * @throws DataAccessException thrown if there is an error updating game data
     */
    void updateGame(GameData game) throws DataAccessException;

    /**
     * Full reset of a game, clears AuthData, GameData, and UserData
     *
     * @throws DataAccessException thrown if there is an error clearing from data
     */
    void clear() throws DataAccessException;

}