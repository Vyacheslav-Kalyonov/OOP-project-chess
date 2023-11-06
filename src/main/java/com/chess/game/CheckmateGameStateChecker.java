package com.chess.game;

import com.chess.coordinates.Color;
import com.chess.board.Board;
import com.chess.board.BoardFactory;
import com.chess.board.Move;
import com.chess.coordinates.Coordinates;
import com.chess.piece.King;
import com.chess.piece.Piece;

import java.util.List;
import java.util.Set;

public class CheckmateGameStateChecker extends GameStateChecker {
    @Override
    public GameState check(Board board, Color color) {
        // проверить, что король под шахом 
        // проверим, есть ли возможные ходы для избавления от шаха
        
        Piece king = board.getPiecesByColor(color).stream().filter(piece -> piece instanceof King).findFirst().get();
        
        if (!board.isSquareAttackedByColor(king.coordinates, color.opposite())) {
            return GameState.ONGOING;
        }

        List<Piece> pieces = board.getPiecesByColor(color);

        for (Piece piece : pieces) {
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);

            for (Coordinates coordinates : availableMoveSquares) {
                Board clone = new BoardFactory().copy(board);
                clone.makeMove(new Move(piece.coordinates, coordinates));

                Piece cloneKing = clone.getPiecesByColor(color).stream().filter(p -> p instanceof King).findFirst().get();


                if (!clone.isSquareAttackedByColor(cloneKing.coordinates, color.opposite())) {
                    return GameState.ONGOING;
                }
            }
        }

        if (color == Color.WHITE) {
            return GameState.CHECKMATE_TO_WHITE_KING;
        } else {
            return GameState.CHECKMATE_TO_BLACK_KING;
        }
    }
}
