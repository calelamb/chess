package ui;

import chess.ChessBoard;
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
                        printGames(games);
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
                        List<GameData> games = new ArrayList<>(s.listGames(a.authToken()));
                        GameData selectedGame = getSelectedGame(games, tokens[1]);
                        s.joinGame(a.authToken(), selectedGame.gameID(), tokens[2]);
                        ChessBoard board = new ChessBoard();
                        board.resetBoard();
                        new BoardRenderer().drawBoard(board, tokens[2].equals("WHITE"));
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "observe" -> {
                    try {
                        List<GameData> games = new ArrayList<>(s.listGames(a.authToken()));
                        GameData selectedGame = getSelectedGame(games, tokens[1]);
                        s.joinGame(a.authToken(), selectedGame.gameID(), null);
                        ChessBoard board = new ChessBoard();
                        board.resetBoard();
                        new BoardRenderer().drawBoard(board, true);

                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                default -> System.out.println("Unknown Command");
            }
        }
    }
}
