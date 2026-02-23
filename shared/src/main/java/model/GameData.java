package model;

import chess.ChessGame;

/**
 * GameData record that stores game data and represents a unique Chess Game
 *
 * @param gameID
 * @param whiteUsername
 * @param blackUsername
 * @param gameName
 * @param game
 */
public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
}
