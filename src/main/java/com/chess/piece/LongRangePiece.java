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
        if (this.getCoordinates().getFile() == shiftedCoordinates.getFile()) {
            coordinatesBetween = BoardUtils.getVerticalCoordinatesBetween(this.getCoordinates(), shiftedCoordinates);
        } else if (this.getCoordinates().getRank() == shiftedCoordinates.getRank()) {
            coordinatesBetween = BoardUtils.getHorizontalCoordinatesBetween(this.getCoordinates(), shiftedCoordinates);
        } else {
            coordinatesBetween = BoardUtils.getDiagonalCoordinatesBetween(this.getCoordinates(), shiftedCoordinates);
        }

        for (Coordinates c: coordinatesBetween) {
            if (!board.isSquareEmpty(c)) {
                return false;
            }
        }
        return true;
    }
}
