package server;

import io.javalin.http.Context;
import model.AuthData;
import model.UserData;
import service.LoginService;
import service.UnauthorizedException;

/**
 *
 */
public class LoginHandler extends Handler {

    private final LoginService l;

    public LoginHandler(LoginService l) {
        this.l = l;
    }

    /**
     *
     * @param ctx
     * @throws Exception
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
        }
    }
}
