package ui;

import chess.ChessGame;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.DataAccessException;
import exception.UnauthorizedException;
import model.AuthData;
import model.GameData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Scanner;

public class PostloginRepl {
    ServerFacade s;
    Scanner scanner;
    AuthData a;

    public PostloginRepl(ServerFacade s, AuthData auth, Scanner scanner) {
        this.s = s;
        this.scanner = scanner;
        this.a = auth;
    }

    public void run() throws UnauthorizedException, BadRequestException, URISyntaxException,
            IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        while (true) {
            System.out.print("[LOGGED IN] >>>");
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            switch (tokens[0]) {
                case "help" -> {
                    System.out.println("logout");
                    System.out.println("list");
                    System.out.println("create <gameName>");
                    System.out.println("play <gameNumber> <WHITE|BLACK>");
                    System.out.println("observe <gameNumber>");
                    System.out.println("help");
                }

                case "logout" -> {
                    s.logoutUser(a.authToken());
                    return;
                }

                case "list" -> {
                    Collection<GameData> games = s.listGames(a.authToken());
                    int counter = 0;
                    for (GameData game : games) {
                        System.out.print(game.gameName());
                    }
                }
            }
        }
    }
}
