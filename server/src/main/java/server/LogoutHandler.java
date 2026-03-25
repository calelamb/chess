package server;

import exception.DataAccessException;
import io.javalin.http.Context;
import service.LogoutService;
import exception.UnauthorizedException;

/**
 * Handles the HTTP DELETE request for the /session endpoint. Logs the user out by deleting the authorization data.
 */
public class LogoutHandler extends Handler {

    private final LogoutService l;

    public LogoutHandler(LogoutService l) {
        this.l = l;
    }

    /**
     * Takes in a context header and ends the user's session by deleting the auth data.
     *
     * @param ctx Context object passed in
     * @throws Exception thrown if there's an error accessing the auth token.
     */
    public void handle(Context ctx) throws Exception {
        try {
            String header = ctx.header("authorization");
            l.endSession(header);

            ctx.status(200);
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.result(serialize(new ErrorMessage("Error: unauthorized")));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(serialize(new ErrorMessage("Error: " + e.getMessage())));
        }
    }
}
