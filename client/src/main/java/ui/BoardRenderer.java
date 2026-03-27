package ui;

import chess.ChessBoard;
import chess.ChessPiece;

import static ui.EscapeSequences.*;

public class BoardRenderer {
    public void drawBoard(ChessBoard board, boolean whiteView) {

    }

    private String getPieceSymbol(ChessPiece piece) {
       if (piece == null) {
           return EMPTY;
       }

       switch (piece.getPieceType()) {
           case ""
       }
    }

    private String getSquareColor(int row, int col) {
        if ((row + col) % 2 == 0) {
            return SET_BG_COLOR_LIGHT_GREY;
        } else {
            return SET_BG_COLOR_DARK_GREEN;
        }
    }

    public static void main(String args[]) {

    }
}
