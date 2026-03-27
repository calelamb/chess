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
                    try {
                        s.logoutUser(a.authToken());
                        return;
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "list" -> {
                    try {
                        Collection<GameData> games = s.listGames(a.authToken());
                        int counter = 1;
                        for (GameData game : games) {
                            System.out.println(counter + ": " + game.gameName());
                            counter++;
                        }
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "create" -> {
                    try {
                        s.createGame(a.authToken(), tokens[1]);
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "play" -> {
                    try {
                        s.joinGame(a.authToken(), Integer.parseInt(tokens[1]), tokens[2]);
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "observe" -> {
                    try {
                        s.joinGame(a.authToken(), Integer.parseInt(tokens[1]), null);
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                default -> System.out.println("Unknown Command");
            }
        }
    }
}
