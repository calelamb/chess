package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;

/**
 * ClearService class for the Clear endpoint.
 */
public class ClearService {

    private final DataAccess data;

    public ClearService(DataAccess d) {
        this.data = d;
    }

    /**
     * Clears the data from the DataAccess instance variable.
     *
     * @throws DataAccessException thrown if there's an error clearing the data
     */
    public void clear() throws DataAccessException {
        data.clear();
    }
}
