package server;

import io.javalin.http.Context;
import model.AuthData;
import model.UserData;
import service.RegisterService;

/**
 *
 */
public class RegisterHandler extends Handler {

    private final RegisterService r;

    public RegisterHandler(RegisterService r) {
        this.r = r;
    }

    /**
     *
     * @param ctx
     * @throws Exception
     */
    public void handle(Context ctx) throws Exception {
        String body = ctx.body();
        UserData userData = deserialize(body, UserData.class);

        AuthData newAuth = r.createNewUser(userData);
        ctx.status(200);
        ctx.result(serialize(newAuth));
    }
}
