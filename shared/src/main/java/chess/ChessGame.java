package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {


    private TeamColor teamTurn;
    private ChessBoard gameState;

    public ChessGame() {

        this.teamTurn = TeamColor.WHITE;
        this.gameState = new ChessBoard();
        gameState.resetBoard();

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Takes in a ChessBoard object and returns a copy of the board
     *
     * @param board the ChessBoard object to be copied
     * @return a copy of the ChessBoard
     */
    public ChessBoard copyBoard(ChessBoard board) {

        ChessBoard copy = new ChessBoard();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                copy.addPiece(new ChessPosition(i, j), piece);
            }
        }
        return copy;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessBoard originalBoard = this.gameState;

        ChessPiece piece = gameState.getPiece(startPosition);
        if (piece == null) {
            return null;
        }

        Collection<ChessMove> pieceMoves = piece.pieceMoves(gameState, startPosition);
        for (ChessMove move : pieceMoves) {
            ChessBoard copy = copyBoard(gameState);
            copy.addPiece(move.getEndPosition(), piece);
            copy.addPiece(move.getStartPosition(), null);
            gameState = copy;

            if (!isInCheck(piece.getTeamColor())) {
                validMoves.add(move);
            }
            gameState = originalBoard;
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //find the king
        ChessPosition kingPosition = null;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece currentPiece = gameState.getPiece(new ChessPosition(i, j));
                if (currentPiece != null && (currentPiece.getPieceType() == ChessPiece.PieceType.KING)) {
                    if (currentPiece.getTeamColor() == teamColor) {
                        kingPosition = new ChessPosition(i, j);
                    }
                }
            }
        }

        //get piece moves for each piece
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece currentPiece = gameState.getPiece(new ChessPosition(i, j));
                if (currentPiece == null) {
                    continue;
                } else if (currentPiece.getTeamColor() != teamColor) {
                    Collection<ChessMove> movesCheck = currentPiece.pieceMoves(gameState, new ChessPosition(i, j));
                    for (ChessMove move : movesCheck) {
                        if (move.getEndPosition().equals(kingPosition)) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameState = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameState;
    }
}
