package server;

import io.javalin.http.Context;
import service.LogoutService;
import service.UnauthorizedException;

public class LogoutHandler extends Handler {

    private final LogoutService l;

    public LogoutHandler(LogoutService l) {
        this.l = l;
    }

    public void handle(Context ctx) throws Exception {
        try {
            String header = ctx.header("authorization");
            l.endSession(header);

            ctx.status(200);
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.result(serialize(new ErrorMessage("Error: unauthorized")));
        }
    }
}
