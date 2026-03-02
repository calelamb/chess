package server;

import dataaccess.MemoryDataAccess;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    private final MemoryDataAccess m;
    var dataAccess = new MemoryDataAccess();
    var clearHandler = new ClearHandler(new ClearService(dataAccess));
    var registerHandler = new RegisterHandler(new RegisterService(dataAccess));


    public Server(MemoryDataAccess m) {
        this.m = m;
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

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
