package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;

public class ClearService {

    private DataAccess data;

    public ClearService(DataAccess d) {
        this.data = d;
    }

    public void clear() throws DataAccessException {
        data.clear();
    }
}
