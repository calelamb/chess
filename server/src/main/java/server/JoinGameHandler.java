package server;

import dataaccess.DataAccessException;
import io.javalin.http.Context;

import service.AlreadyTakenException;
import service.BadRequestException;
import service.JoinGameService;
import service.UnauthorizedException;

/**
 * Handles the HTTP PUT request for the /game endpoint, joining an existing chess game
 */
public class JoinGameHandler extends Handler {

    private final JoinGameService j;

    private record JoinGameRequest(String playerColor, int gameID) {
    }

    public JoinGameHandler(JoinGameService j) {
        this.j = j;
    }

    /**
     * Reads the auth token from the header and player color and game ID from the request body, then joins the user to the specified game.
     *
     * @param ctx Context object passed in
     * @throws Exception thrown if there's an error joining the game
     */
    public void handle(Context ctx) throws Exception {
        try {
            String header = ctx.header("authorization");
            JoinGameRequest gameName = deserialize(ctx.body(), JoinGameRequest.class);
            j.joinGame(header, gameName.playerColor(), gameName.gameID());
            ctx.status(200);
        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.result(serialize(new ErrorMessage("Error: unauthorized")));
        } catch (BadRequestException e) {
            ctx.status(400);
            ctx.result(serialize(new ErrorMessage("Error: bad request")));
        } catch (AlreadyTakenException e) {
            ctx.status(403);
            ctx.result(serialize(new ErrorMessage("Error: already taken")));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(serialize(new ErrorMessage("Error: " + e.getMessage())));
        }
    }
}
