package server;

import exception.DataAccessException;
import io.javalin.http.Context;
import service.ClearService;

/**
 * Handles the HTTP DELETE request for the /db endpoint, clearing all data from the server.
 */
public class ClearHandler extends Handler {

    private final ClearService c;

    public ClearHandler(ClearService c) {
        this.c = c;
    }

    /**
     * Handler for the ClearService, clears data from the server and sends a 200 status message.
     *
     * @param ctx the Javalin context object for the HTTP request and response
     * @throws Exception thrown if there was an error clearing the data
     */
    public void handle(Context ctx) throws Exception {
        try {
            c.clear();
            ctx.status(200);
            ctx.result("{}");
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(serialize(new ErrorMessage("Error: " + e.getMessage())));
        }
    }
}
