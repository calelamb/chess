package ui;

import chess.ChessBoard;
import chess.ChessGame;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.DataAccessException;
import exception.UnauthorizedException;
import model.AuthData;
import model.GameData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    private String formatSeat(String color, String username) {
        if (username == null) {
            return color + ": open";
        } else {
            return color + ": " + username;
        }
    }

    private String formatGameLine(int number, GameData game) {
        String whiteSeat = formatSeat("WHITE", game.whiteUsername());
        String blackSeat = formatSeat("BLACK", game.blackUsername());
        return number + ": " + game.gameName() + " | " + whiteSeat + " | " + blackSeat;
    }

    private GameData getSelectedGame(List<GameData> games, String input) throws BadRequestException {
        int index;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid game number");
        }

        if (index < 0 || index >= games.size()) {
            throw new BadRequestException("Invalid game number");
        }

        return games.get(index);

    }

    private boolean hasExpectedArgs(String[] tokens, int expected) {
        if (tokens.length != expected) {
            System.out.println("error: invalid amount of parameters");
            return false;
        } else {
            return true;
        }
    }

    private void printGames(Collection<GameData> games) {
        int counter = 1;
        for (GameData game : games) {
            System.out.println(formatGameLine(counter, game));
            counter++;
        }
    }

    public void run() throws UnauthorizedException, BadRequestException, URISyntaxException,
            IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        while (true) {
            System.out.print("[LOGGED IN] >>>");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("error: invalid amount of parameters");
                continue;
            }
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
                        printGames(games);
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "create" -> {
                    if (!hasExpectedArgs(tokens, 2)) {
                        break;
                    }
                    try {
                        s.createGame(a.authToken(), tokens[1]);
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "play" -> {
                    if (!hasExpectedArgs(tokens, 3)) {
                        break;
                    }
                    try {
                        List<GameData> games = new ArrayList<>(s.listGames(a.authToken()));
                        GameData selectedGame = getSelectedGame(games, tokens[1]);
                        s.joinGame(a.authToken(), selectedGame.gameID(), tokens[2]);
                        ChessGame game = new ChessGame();
                        game.getBoard().resetBoard();
                        GameplayRepl repl = new GameplayRepl(a.authToken(), selectedGame.gameID(), null, scanner, tokens[2], game);
                        WebSocketFacade facade = new WebSocketFacade("ws://localhost:8080/ws", repl);
                        repl.setFacade(facade);
                        facade.connect();
                        facade.sendConnect(a.authToken(), selectedGame.gameID());
                        repl.run();
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "observe" -> {
                    if (!hasExpectedArgs(tokens, 2)) {
                        break;
                    }
                    try {
                        List<GameData> games = new ArrayList<>(s.listGames(a.authToken()));
                        GameData selectedGame = getSelectedGame(games, tokens[1]);
                        s.joinGame(a.authToken(), selectedGame.gameID(), null);
                        ChessGame game = new ChessGame();
                        game.getBoard().resetBoard();
                        GameplayRepl repl = new GameplayRepl(a.authToken(), selectedGame.gameID(), null, scanner, null, game);
                        WebSocketFacade facade = new WebSocketFacade("ws://localhost:8080/ws", repl);
                        repl.setFacade(facade);
                        facade.connect();
                        facade.sendConnect(a.authToken(), selectedGame.gameID());
                        repl.run();

                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                default -> System.out.println("Unknown Command");
            }
        }
    }
}
