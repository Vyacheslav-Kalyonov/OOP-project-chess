package com.chess.piece;

import com.chess.coordinates.CoordinatesShift;

import java.util.HashSet;
import java.util.Set;

public interface IRook {

    default Set<CoordinatesShift> getRockMoves() {
        Set<CoordinatesShift> result = new HashSet<>();

        // слева снизу -> сверху справа
        for (int i = -7; i <= 7; i++) {
            if (i == 0) continue;

            result.add(new CoordinatesShift(i, 0));
        }

        for (int i = -7; i <= 7; i++) {
            if (i == 0) continue;

            result.add(new CoordinatesShift(0, i));
        }
        return result;
    }
}
