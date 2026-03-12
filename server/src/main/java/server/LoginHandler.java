package server;

import dataaccess.DataAccessException;
import io.javalin.http.Context;
import model.AuthData;
import model.UserData;
import service.BadRequestException;
import service.LoginService;
import service.UnauthorizedException;

/**
 * Handles the HTTP POST request for the endpoint, logging the user in with a validated authorization token.
 */
public class LoginHandler extends Handler {

    private final LoginService l;

    public LoginHandler(LoginService l) {
        this.l = l;
    }

    /**
     * Deserializes the request body into a UserData object, and logs the user in with their authorization token.
     *
     * @param ctx Context object passed in
     * @throws Exception thrown if there's an error
     */
    public void handle(Context ctx) throws Exception {
        try {
            String body = ctx.body();
            UserData userData = deserialize(body, UserData.class);

            AuthData newAuth = l.loginUser(userData);
            ctx.status(200);
            ctx.result(serialize(newAuth));
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.result(serialize(new ErrorMessage("Error: unauthorized")));
        } catch (BadRequestException e) {
            ctx.status(400);
            ctx.result(serialize(new ErrorMessage("Error: bad request")));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(serialize(new ErrorMessage("Error: " + e.getMessage())));
        }
    }
}
