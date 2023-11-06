package com.chess.piece;

import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;

import java.util.List;

public abstract class LongRangePiece extends Piece {

    public LongRangePiece(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        boolean result = super.isSquareAvailableForMove(coordinates, board);

        if (result) {
            return isSquareAvailableForAttack(coordinates, board);
        } else {
            return false;
        }
    }

    @Override
    protected boolean isSquareAvailableForAttack(Coordinates shiftedCoordinates, Board board) {
        List<Coordinates> coordinatesBetween;
        if (this.coordinates.file == shiftedCoordinates.file) {
            coordinatesBetween = BoardUtils.getVerticalCoordinatesBetween(this.coordinates, shiftedCoordinates);
        } else if (this.coordinates.rank == shiftedCoordinates.rank) {
            coordinatesBetween = BoardUtils.getHorizontalCoordinatesBetween(this.coordinates, shiftedCoordinates);
        } else {
            coordinatesBetween = BoardUtils.getDiagonalCoordinatesBetween(this.coordinates, shiftedCoordinates);
        }

        for (Coordinates c: coordinatesBetween) {
            if (!board.isSquareEmpty(c)) {
                return false;
            }
        }
        return true;
    }
}
