package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GameplayRepl implements ServerMessageObserver {
    private final String authToken;
    private final int gID;
    private WebSocketFacade facade;
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

    public void setFacade(WebSocketFacade f) {
        this.facade = f;
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
                        int startCol = tokens[1].charAt(0) - 'a' + 1;
                        int startRow = Character.getNumericValue(tokens[1].charAt(1));
                        int endCol = tokens[2].charAt(0) - 'a' + 1;
                        int endRow = Character.getNumericValue(tokens[2].charAt(1));
                        ChessMove move = new ChessMove(new ChessPosition(startRow, startCol), new ChessPosition(endRow, endCol), null);
                        facade.sendMakeMove(authToken, gID, move);
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "highlight" -> {
                    if (!hasExpectedArgs(tokens, 2)) {
                        break;
                    }
                    try {
                        int col = tokens[1].charAt(0) - 'a' + 1;
                        int row = Character.getNumericValue(tokens[1].charAt(1));
                        ChessPosition selected = new ChessPosition(row, col);
                        Collection<ChessMove> moves = game.validMoves(selected);
                        if (moves == null || moves.isEmpty()) {
                            System.out.println("No valid moves for that square");
                            break;
                        }
                        Set<ChessPosition> highlights = new HashSet<>();
                        for (ChessMove m : moves) {
                            highlights.add(m.getEndPosition());
                        }
                        boolean whiteView = color == null || color.equals("WHITE");
                        new BoardRenderer().drawBoard(game.getBoard(), whiteView, highlights, selected);
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                default -> System.out.println("Unknown Command");
            }
        }
    }
}
