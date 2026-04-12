package ui;

import chess.ChessGame;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.DataAccessException;
import exception.UnauthorizedException;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class GameplayRepl implements ServerMessageObserver {
    private final String authToken;
    private final int gID;
    private final WebSocketFacade facade;
    private final Scanner input;
    private final String color;
    private ChessGame game;

    public GameplayRepl(String authToken, int gID, WebSocketFacade wsf, Scanner input, String color, ChessGame game) {

        this.authToken = authToken;
        this.gID = gID;
        this.facade = wsf;
        this.input = input;
        this.color = color;
        this.game = game;
    }

    @Override
    public void notifyMessage(String json, ServerMessage message) {
        if (message instanceof LoadGameMessage m) {
            game = m.getGame();
            new BoardRenderer().drawBoard(game.getBoard(), color.equals("WHITE"));
        } else if (message instanceof ErrorMessage m) {
            System.out.println(m.getErrorMessage());
        } else if (message instanceof NotificationMessage m) {
            System.out.println(m.getMessage());
        }
    }

    private boolean hasExpectedArgs(String[] tokens, int expected) {
        if (tokens.length != expected) {
            System.out.println("error: invalid amount of parameters");
            return false;
        } else {
            return true;
        }
    }

    public void run() throws UnauthorizedException, BadRequestException, URISyntaxException,
            IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        while (true) {
            System.out.print("[IN GAME] >>>");
            String line = input.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("error: invalid amount of parameters");
                continue;
            }
            String[] tokens = line.split(" ");
            switch (tokens[0]) {
                case "help" -> {
                    System.out.println("redraw");
                    System.out.println("move <from> <to>");
                    System.out.println("leave");
                    System.out.println("resign");
                    System.out.println("highlight");
                    System.out.println("help");

                }

                case "redraw" -> {
                    try {
                        new BoardRenderer().drawBoard(game.getBoard(), color.equals("WHITE"));
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "leave" -> {
                    try {
                        facade.sendLeave(authToken, gID);
                        return;
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "resign" -> {
                    try {
                        facade.sendResign(authToken, gID);
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "move" -> {
                    if (!hasExpectedArgs(tokens, 3)) {
                        break;
                    }
                    try {
                        // todo
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "highlight" -> {
                    if (!hasExpectedArgs(tokens, 2)) {
                        break;
                    }
                    try {
                        //todo

                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                default -> System.out.println("Unknown Command");
            }
        }
    }
}
