package com.chess.board;

import com.chess.coordinates.Coordinates;
import com.chess.coordinates.File;

import java.util.ArrayList;
import java.util.List;

public class BoardUtils {

    public static List<Coordinates> getDiagonalCoordinatesBetween(Coordinates source, Coordinates target) {
        // клетки лежат на одной диагонали
        List<Coordinates> result = new ArrayList<>();

        // проверки направления движения
        int fileShift;
        if (source.getFile().ordinal() < target.getFile().ordinal()) {
            fileShift = 1;
        } else {
            fileShift = -1;
        }

        int rankShift;
        if (source.getRank() < target.getRank()) {
            rankShift = 1;
        } else {
            rankShift = -1;
        }

        for (
                int fileIndex = source.getFile().ordinal() + fileShift,
                rank = source.getRank() + rankShift;

                fileIndex != target.getFile().ordinal() && rank != target.getRank();

                fileIndex += fileShift, rank += rankShift
        ) {
            result.add(new Coordinates(File.values()[fileIndex], rank));
        }

        return result;
    }

    public static List<Coordinates> getVerticalCoordinatesBetween(Coordinates source, Coordinates target) {
        // клетки лежат на одной диагонали
        List<Coordinates> result = new ArrayList<>();

        // проверки направления движения
        int rankShift;
        if (source.getRank() < target.getRank()) {
            rankShift = 1;
        } else {
            rankShift = -1;
        }

        for (int rank = source.getRank() + rankShift; rank != target.getRank(); rank += rankShift) {
            result.add(new Coordinates(source.getFile(), rank));
        }

        return result;
    }

    public static List<Coordinates> getHorizontalCoordinatesBetween(Coordinates source, Coordinates target) {
        // клетки лежат на одной диагонали
        List<Coordinates> result = new ArrayList<>();

        // проверки направления движения
        int fileShift;
        if (source.getFile().ordinal() < target.getFile().ordinal()) {
            fileShift = 1;
        } else {
            fileShift = -1;
        }

        for (int fileIndex = source.getFile().ordinal() + fileShift; fileIndex != target.getFile().ordinal(); fileIndex += fileShift) {
            result.add(new Coordinates(File.values()[fileIndex], source.getRank()));
        }

        return result;
    }
}