package com.chess.coordinates;

public class CoordinatesShift {
    private final int fileShift;
    private final int rankShift;

    public CoordinatesShift(int fileShift, int rankShift) {
        this.fileShift = fileShift;
        this.rankShift = rankShift;
    }

    public int getFileShift() {
        return fileShift;
    }

    public int getRankShift() {
        return rankShift;
    }
}
