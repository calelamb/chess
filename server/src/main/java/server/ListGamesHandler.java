package server;

import dataaccess.DataAccessException;
import io.javalin.http.Context;
import model.GameData;
import service.ListGamesService;
import service.UnauthorizedException;

import java.util.Collection;

/**
 * Handles the HTTP GET request for the /game endpoint. Lists the current games under an authorization token
 */
public class ListGamesHandler extends Handler {

    private final ListGamesService l;

    private record ListGamesResponse(Collection<GameData> games) {
    }

    public ListGamesHandler(ListGamesService l) {
        this.l = l;
    }

    /**
     * Takes in a context header and lists the games on the current session
     *
     * @param ctx Context object passed in
     * @throws Exception thrown if there's an error getting the games
     */
    public void handle(Context ctx) throws Exception {
        try {
            String header = ctx.header("authorization");
            Collection<GameData> list = l.listGames(header);

            ctx.status(200);
            ctx.result(serialize(new ListGamesResponse(list)));

        } catch (UnauthorizedException e) {
            ctx.status(401);
            ctx.result(serialize(new ErrorMessage("Error: unauthorized")));
        } catch (DataAccessException e) {
            ctx.status(500);
            ctx.result(serialize(new ErrorMessage("Error: " + e.getMessage())));
        }
    }
}
