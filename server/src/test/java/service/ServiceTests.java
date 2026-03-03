package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    private MemoryDataAccess dataAccess;

    @BeforeEach
    public void setup() throws Exception {
        dataAccess = new MemoryDataAccess();
        new ClearService(dataAccess).clear();
    }

    @Test
    public void clearPositive() throws DataAccessException {
        dataAccess.createUser(new UserData("asdf", "asdf", "asdf@gmail.com"));
        dataAccess.clear();
        assertNull(dataAccess.getUser("asdf"));
    }

    @Test
    public void

}