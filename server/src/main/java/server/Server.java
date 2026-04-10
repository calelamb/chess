package server;

import dataaccess.DataAccess;

import exception.DataAccessException;
import dataaccess.MySqlDataAccess;
import io.javalin.*;
import service.*;

public class Server {

    private final Javalin javalin;


    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        DataAccess dataAccess;
        try {
            dataAccess = new MySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        var clearHandler = new ClearHandler(new ClearService(dataAccess));
        var registerHandler = new RegisterHandler(new RegisterService(dataAccess));
        var loginHandler = new LoginHandler(new LoginService(dataAccess));
        var logoutHandler = new LogoutHandler(new LogoutService(dataAccess));
        var listGamesHandler = new ListGamesHandler(new ListGamesService(dataAccess));
        var createGameHandler = new CreateGameHandler(new CreateGameService(dataAccess));
        var joinGameHandler = new JoinGameHandler(new JoinGameService(dataAccess));
        var websocketHandler = new WebSocket();

        javalin.delete("/db", clearHandler::handle);
        javalin.post("/user", registerHandler::handle);
        javalin.post("/session", loginHandler::handle);
        javalin.delete("/session", logoutHandler::handle);
        javalin.get("/game", listGamesHandler::handle);
        javalin.post("/game", createGameHandler::handle);
        javalin.put("/game", joinGameHandler::handle);


        javalin.ws("/ws", ws -> {
            ws.onConnect(ctx -> {
            });
            ws.onMessage(ctx -> {
                websocketHandler.handleMessage(ctx.session, ctx.message());
            });
            ws.onError(ctx -> {
            });
            ws.onClose(ctx -> {
            });
        });

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
