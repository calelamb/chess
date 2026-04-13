package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Set;

import static ui.EscapeSequences.*;

public class BoardRenderer {
    public void drawBoard(ChessBoard board, boolean whiteView) {
        drawBoard(board, whiteView, Set.of(), null);
    }

    public void drawBoard(ChessBoard board, boolean whiteView, Set<ChessPosition> highlights, ChessPosition selected) {
        if (highlights == null) {
            highlights = Set.of();
        }
        int start, end, step;
        if (whiteView) {
            start = 8;
            end = 1;
            step = -1;
        } else {
            start = 1;
            end = 8;
            step = 1;
        }

        String cols = whiteView ? "  a  b  c  d  e  f  g  h  " : "  h  g  f  e  d  c  b  a  ";
        System.out.println(SET_BG_COLOR_DARK_GREY + cols + RESET_BG_COLOR);
        for (int row = start; row != end + step; row += step) {
            System.out.print(SET_BG_COLOR_DARK_GREY + " " + row + " " + RESET_BG_COLOR);
            int colStart, colEnd, colStep;
            if (whiteView) {
                colStart = 1;
                colEnd = 8;
                colStep = 1;
            } else {
                colStart = 8;
                colEnd = 1;
                colStep = -1;
            }
            for (int col = colStart; col != colEnd + colStep; col += colStep) {
                ChessPosition pos = new ChessPosition(row, col);
                System.out.print(getSquareColor(row, col, pos, highlights, selected) + getPieceSymbol(board.getPiece(pos)));
            }
            System.out.println(RESET_BG_COLOR + SET_BG_COLOR_DARK_GREY + " " + row + " " + RESET_BG_COLOR);

        }
        System.out.println(SET_BG_COLOR_DARK_GREY + cols + RESET_BG_COLOR);
    }

    private String getPieceSymbol(ChessPiece piece) {
        if (piece == null) {
            return EMPTY;
        }

        switch (piece.getPieceType()) {
            case PAWN -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return WHITE_PAWN;
                } else {
                    return BLACK_PAWN;
                }
            }

            case ROOK -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return WHITE_ROOK;
                } else {
                    return BLACK_ROOK;
                }
            }

            case KNIGHT -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return WHITE_KNIGHT;
                } else {
                    return BLACK_KNIGHT;
                }
            }
            case BISHOP -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return WHITE_BISHOP;
                } else {
                    return BLACK_BISHOP;
                }
            }
            case QUEEN -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return WHITE_QUEEN;
                } else {
                    return BLACK_QUEEN;
                }
            }

            case KING -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    return WHITE_KING;
                } else {
                    return BLACK_KING;
                }
            }

            default -> {
                return EMPTY;
            }

        }
    }

    private String getSquareColor(int row, int col, ChessPosition pos,
                                  Set<ChessPosition> highlights, ChessPosition selected) {
        if (pos.equals(selected)) {
            return SET_BG_COLOR_YELLOW;
        }
        if (highlights.contains(pos)) {
            return SET_BG_COLOR_GREEN;
        }
        if ((row + col) % 2 == 0) {
            return SET_BG_COLOR_LIGHT_GREY;
        } else {
            return SET_BG_COLOR_DARK_GREEN;
        }
    }

}
