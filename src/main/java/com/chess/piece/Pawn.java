package com.chess.piece;

import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.CoordinatesShift;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {
    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> result = new HashSet<>();
        if (color == Color.WHITE) {
            result.add(new CoordinatesShift(0, 1));

            if (coordinates.rank == 2) {
                result.add(new CoordinatesShift(0, 2));
            }

            result.add(new CoordinatesShift(1, 1));
            result.add(new CoordinatesShift(-1, 1));
        } else {
            result.add(new CoordinatesShift(0, -1));

            if (coordinates.rank == 7) {
                result.add(new CoordinatesShift(0, -2));
            }

            result.add(new CoordinatesShift(1, -1));
            result.add(new CoordinatesShift(-1, -1));
        }

        return result;
    }

    @Override
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        if (this.coordinates.file == coordinates.file) {
            int rankShift = Math.abs(this.coordinates.rank - coordinates.rank);

            if (rankShift == 2) {
                List<Coordinates> between = BoardUtils.getVerticalCoordinatesBetween(this.coordinates, coordinates);

                return (board.isSquareEmpty(between.get(0)) && board.isSquareEmpty(coordinates));
            } else {
                return board.isSquareEmpty(coordinates);
            }

        } else {
            if (board.isSquareEmpty(coordinates)) {
                return false;
            } else {
                return board.getPiece(coordinates).color != color;
            }
        }
    }

    @Override
    protected Set<CoordinatesShift> getPieceAttacks() {
        Set<CoordinatesShift> result = new HashSet<>();

        if (color == Color.WHITE) {
            result.add(new CoordinatesShift(1, 1));
            result.add(new CoordinatesShift(-1, 1));
        } else {
            result.add(new CoordinatesShift(1, -1));
            result.add(new CoordinatesShift(-1, -1));
        }

        return result;
    }


}
