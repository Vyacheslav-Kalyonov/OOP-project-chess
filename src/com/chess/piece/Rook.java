package com.chess.piece;

import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.CoordinatesShift;

import java.util.Set;

public class Rook extends LongRangePiece implements IRook {
    public Rook(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        return getRockMoves();
    }
}
