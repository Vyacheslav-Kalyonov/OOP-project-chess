package com.chess.coordinates;

import java.util.Objects;

public class Coordinates {

    private final File file; // неизменяемые координаты

    private final Integer rank; // неизменяемые координаты

    public Coordinates(File file, Integer rank) {
        this.file = file;
        this.rank = rank;
    }

    public Coordinates shift(CoordinatesShift shift) {
        return new Coordinates(File.values()[this.file.ordinal() + shift.getFileShift()], this.rank + shift.getRankShift());
    }

    public boolean canShift(CoordinatesShift shift) {
        int f = file.ordinal() + shift.getFileShift();
        int r = rank + shift.getRankShift();

        return !((f < 0 || f > 7) || (r < 1 || r > 8));
    }

    public File getFile() {
        return file;
    }

    public Integer getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return file == that.file && Objects.equals(rank, that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    @Override
    public String toString() {
        return file.name() + rank;
    }
}
