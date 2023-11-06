package com.chess.piece;

import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.CoordinatesShift;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends LongRangePiece {
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected Set<CoordinatesShift> getPieceMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        // слева снизу -> сверху справа
        for (int i = -7; i <= 7; i++) {
            if (i == 0) continue;

            result.add(new CoordinatesShift(i, i));
            result.add(new CoordinatesShift(i, -i));
        }

        return result;
    }
}
