package com.chess.game;

import com.chess.coordinates.Color;
import com.chess.board.Board;
import com.chess.coordinates.Coordinates;
import com.chess.piece.Piece;

import java.util.List;
import java.util.Set;

public class StaleMateGameChecker extends GameStateChecker {

    @Override
    public GameState check(Board board, Color color) {
        List<Piece> pieces = board.getPiecesByColor(color);

        for (Piece piece : pieces) {
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            if (!availableMoveSquares.isEmpty()) {
                return GameState.ONGOING;
            }
        }

        return GameState.STALEMATE;
    }
}
