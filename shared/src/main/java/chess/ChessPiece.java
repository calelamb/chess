package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;

    }

    public boolean validBounds(ChessPosition pos) {
        return pos.getRow() >= 1 && pos.getRow() <= 8 && pos.getColumn() >= 1 && pos.getColumn() <= 8;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        ArrayList<ChessMove> moves = new ArrayList<>();

        //king algorithm
        if (piece.getPieceType() == PieceType.KING) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    if (myPosition.getRow() + i >= 1 && myPosition.getColumn() + j >= 1 && myPosition.getRow() + i <= 8 && myPosition.getColumn() + j <= 8) {
                        ChessPosition pos = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + j);
                        ChessPiece pieceCheck = board.getPiece(pos);
                        if (pieceCheck == null || pieceCheck.getTeamColor() != piece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, pos, null));
                        }

                    }
                }
            }
        }

        //rook algorithm
        if (piece.getPieceType() == PieceType.ROOK) {
            //moving up
            for (int i = myPosition.getRow() + 1; i <= 8; i++) {
                ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }
            //moving down
            for (int i = myPosition.getRow() - 1; i >= 1; i--) {
                ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }
            //moving left
            for (int i = myPosition.getColumn() - 1; i >= 1; i--) {
                ChessPosition pos = new ChessPosition(myPosition.getRow(), i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }
            //moving right
            for (int i = myPosition.getColumn() + 1; i <= 8; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow(), i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }
        }

        //bishop algorithm
        if (piece.getPieceType() == PieceType.BISHOP) {
            //moving up right
            for (int i = 1; myPosition.getRow() + i <= 8 && myPosition.getColumn() + i <= 8; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }

            //moving up left
            for (int i = 1; myPosition.getRow() + i <= 8 && myPosition.getColumn() - i >= 1; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }

            //moving down left
            for (int i = 1; myPosition.getRow() - i >= 1 && myPosition.getColumn() - i >= 1; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }

            //moving down right
            for (int i = 1; myPosition.getRow() - i >= 1 && myPosition.getColumn() + i <= 8; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }
        }

        //queen algorithm
        if (piece.getPieceType() == PieceType.QUEEN) {
            //moving up
            for (int i = myPosition.getRow() + 1; i <= 8; i++) {
                ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }
            //moving down
            for (int i = myPosition.getRow() - 1; i >= 1; i--) {
                ChessPosition pos = new ChessPosition(i, myPosition.getColumn());
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }
            //moving left
            for (int i = myPosition.getColumn() - 1; i >= 1; i--) {
                ChessPosition pos = new ChessPosition(myPosition.getRow(), i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }
            //moving right
            for (int i = myPosition.getColumn() + 1; i <= 8; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow(), i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }

            //moving up right
            for (int i = 1; myPosition.getRow() + i <= 8 && myPosition.getColumn() + i <= 8; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }

            //moving up left
            for (int i = 1; myPosition.getRow() + i <= 8 && myPosition.getColumn() - i >= 1; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }

            //moving down left
            for (int i = 1; myPosition.getRow() - i >= 1 && myPosition.getColumn() - i >= 1; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }

            //moving down right
            for (int i = 1; myPosition.getRow() - i >= 1 && myPosition.getColumn() + i <= 8; i++) {
                ChessPosition pos = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                ChessPiece pieceCheck = board.getPiece(pos);
                if (pieceCheck == null) {
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (pieceCheck.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else if (pieceCheck.getTeamColor() == piece.getTeamColor()) {
                    break;
                }
            }

        }
        //knight algorithm
        if (piece.getPieceType() == PieceType.KNIGHT) {
            ChessPosition[] potentialMoves = {new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1), new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() - 1), new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() + 1), new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() - 1), new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2), new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 2), new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 2), new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 2),};

            for (ChessPosition move : potentialMoves) {
                if (move.getRow() >= 1 && move.getColumn() >= 1 && move.getRow() <= 8 && move.getColumn() <= 8) {
                    ChessPiece pieceCheck = board.getPiece(move);
                    if (pieceCheck == null || pieceCheck.getTeamColor() != piece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, move, null));
                    }

                }
            }

        }

        //pawn algorithm
        if (piece.getPieceType() == PieceType.PAWN) {
            int direction;

            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                direction = 1;

            } else {
                direction = -1;
            }

            //forward movement logic
            ChessPosition pos = new ChessPosition(myPosition.getRow() + direction, myPosition.getColumn());
            ChessPiece pieceCheck = board.getPiece(pos);
            if (pieceCheck == null) {
                if (((myPosition.getRow() == 2 && piece.getTeamColor() == ChessGame.TeamColor.WHITE) || (myPosition.getRow() == 7) && piece.getTeamColor() == ChessGame.TeamColor.BLACK)) {
                    ChessPosition twoSquarePos = new ChessPosition(pos.getRow() + direction, pos.getColumn());
                    ChessPiece twoSquareCheck = board.getPiece(twoSquarePos);

                    if (twoSquareCheck == null) {
                        moves.add(new ChessMove(myPosition, twoSquarePos, null));
                    }
                }
                if (pos.getRow() == 1 || pos.getRow() == 8) {
                    moves.add(new ChessMove(myPosition, pos, PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, pos, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, pos, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, pos, PieceType.ROOK));
                } else {
                    moves.add(new ChessMove(myPosition, pos, null));
                }
            }

            //diagonal capture logic
            ChessPosition[] diagonalPositions = {new ChessPosition(myPosition.getRow() + direction, myPosition.getColumn() + 1),
                    new ChessPosition(myPosition.getRow() + direction, myPosition.getColumn() - 1)
            };

            for (ChessPosition position : diagonalPositions) {
                if (validBounds(position)) {
                    ChessPiece diagonalPiece = board.getPiece(position);
                    if (diagonalPiece != null && diagonalPiece.getTeamColor() != piece.getTeamColor()) {
                        if (position.getRow() == 1 || position.getRow() == 8) {
                            moves.add(new ChessMove(myPosition, position, PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, position, PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, position, PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, position, PieceType.ROOK));
                        } else {
                            moves.add(new ChessMove(myPosition, position, null));
                        }
                    }

                }
            }
        }
        return moves;
    }
}
