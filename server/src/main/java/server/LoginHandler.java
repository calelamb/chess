package server;

import io.javalin.http.Context;
import model.AuthData;
import model.UserData;
import service.LoginService;

/**
 *
 */
public class LoginHandler extends Handler {

    private final LoginService l;

    public LoginHandler(LoginService l) {
        this.l = l;
    }

    public void handle(Context ctx) throws Exception {
        String body = ctx.body();
        UserData userData = deserialize(body, UserData.class);

        AuthData newAuth = l.loginUser(userData);
        ctx.status(200);
        ctx.result(serialize(newAuth));
    }
}
