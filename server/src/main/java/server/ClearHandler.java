package server;

import io.javalin.http.Context;
import service.ClearService;

public class ClearHandler extends Handler{

    private final ClearService c;

    public ClearHandler(ClearService c) {
        this.c = c;
    }

    public void handle(Context ctx) throws Exception {
        c.clear();
        ctx.status(200);
        ctx.result("{}");
    }
}
