package server;

import dataaccess.MemoryDataAccess;
import io.javalin.*;
import service.ClearService;
import service.RegisterService;

public class Server {

    private final Javalin javalin;

    private final MemoryDataAccess m;


    public Server(MemoryDataAccess m) {
        this.m = m;
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        var dataAccess = new MemoryDataAccess();
        var clearHandler = new ClearHandler(new ClearService(dataAccess));
        var registerHandler = new RegisterHandler(new RegisterService(dataAccess));

        javalin.delete("/db", clearHandler::handle);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
