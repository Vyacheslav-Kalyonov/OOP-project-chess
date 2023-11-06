package com.chess.board;

import com.chess.coordinates.Coordinates;

public class Move {
    public final Coordinates from, to;

    public Move(Coordinates from, Coordinates coordinates) {
        this.from = from;
        to = coordinates;
    }

    @Override
    public String toString() {
        return from + " " + to;
    }
}
