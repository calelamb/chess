package server;

import io.javalin.http.Context;
import service.CreateGameService;
import service.UnauthorizedException;


/**
 * Handles the HTTP POST request for the /game endpoint, creating a new chess game.
 */
public class CreateGameHandler extends Handler {

    private final CreateGameService c;

    private record GameNameRequest(String gameName) {
    }

    private record GameIDResponse(int gameID) {
    }

    public CreateGameHandler(CreateGameService c) {
        this.c = c;
    }

    /**
     * Reads the auth token from the header and game name from the request body, creates a new game, and writes the new game ID to the response.
     *
     * @param ctx Context object passed in
     * @throws Exception thrown if there's an error creating the game
     */
    public void handle(Context ctx) throws Exception {
        try {
            String header = ctx.header("authorization");
            GameNameRequest gameName = deserialize(ctx.body(), GameNameRequest.class);
            int newID = c.newGame(header, gameName.gameName());
            ctx.status(200);
            ctx.result(serialize(new GameIDResponse(newID)));
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.result(serialize(new ErrorMessage("Error: unauthorized")));
        }
    }
}
