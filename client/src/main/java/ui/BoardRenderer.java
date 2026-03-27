package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;

import static ui.EscapeSequences.*;

public class BoardRenderer {
    public void drawBoard(ChessBoard board, boolean whiteView) {
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
        for (int row = start; row != end + step; row += step) {

        }


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

            default -> return EMPTY;

        }
    }

    private String getSquareColor(int row, int col) {
        if ((row + col) % 2 == 0) {
            return SET_BG_COLOR_LIGHT_GREY;
        } else {
            return SET_BG_COLOR_DARK_GREEN;
        }
    }

}
