package server;

import io.javalin.http.Context;
import model.AuthData;
import model.UserData;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.RegisterService;

/**
 * Handles the HTTP POST request for the /user endpoint, creating a new user with passed in UserData
 */
public class RegisterHandler extends Handler {

    private final RegisterService r;

    public RegisterHandler(RegisterService r) {
        this.r = r;
    }

    /**
     * Deserializes the request body into a UserData object, registers the new user and writes to the response with ctx.
     *
     * @param ctx Context object passed in
     * @throws Exception Thrown if there's an error creating the new user.
     */
    public void handle(Context ctx) throws Exception {
        try {
            String body = ctx.body();
            UserData userData = deserialize(body, UserData.class);

            AuthData newAuth = r.createNewUser(userData);
            ctx.status(200);
            ctx.result(serialize(newAuth));
        } catch (BadRequestException e) {
            ctx.status(400);
            ctx.result(serialize(new ErrorMessage("Error: bad request")));
        } catch (AlreadyTakenException e) {
            ctx.status(403);
            ctx.result(serialize(new ErrorMessage("Error: already taken")));
        }
    }
}
