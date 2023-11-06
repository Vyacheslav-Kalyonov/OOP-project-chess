package com.chess.board;

import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.File;
import com.chess.piece.Piece;

import java.util.Set;

import static java.util.Collections.emptySet;

public class BoardConsoleRenderer {

    // константы для цветов, которые будут применяться в консоли
    public static final String ANSI_PRESET = "\u001B[0m";
    public static final String ANSI_WHITE_PIECE_COLOR = "\u001B[97m";
    public static final String ANSI_BLACK_PIECE_COLOR = "\u001B[30m";
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[0;100m";
    public static final String ANSI_HIGHLIGHTED_SQUARE_BACKGROUND = "\u001B[45m";

    public void render(Board board, Piece pieceToMove) {
        Set<Coordinates> availableMove = emptySet();
        if (pieceToMove != null) {
            availableMove = pieceToMove.getAvailableMoveSquares(board);
        }

        for (int rank = 8; rank >= 1; rank--) {
            String line = "";
            System.out.print(rank);
            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                boolean isHighLight = availableMove.contains(coordinates);

                if (board.isSquareEmpty(coordinates)) {
                    line += getSpriteForEmptySquare(coordinates, isHighLight);
                } else {
                    line += getPieceSprite(board.getPiece(coordinates), isHighLight);
                }
            }

            line += ANSI_PRESET; // сброс цвета, чтобы не было закраски всей консоли
            System.out.println(line);
        }

        System.out.print(" ");
        for (File file : File.values()) {
            System.out.print(file + "\t");
        }
        System.out.println();
    }

    public void render(Board board) {
        render(board, null);
    }

    private String selectUnicodeSpriteForPiece(Piece piece) {
        return switch (PieceForConsoleRender.valueOf(piece.getClass().getSimpleName().toUpperCase())) {
            case PAWN -> "♟︎";
            case KNIGHT -> "♞";
            case BISHOP -> "♝";
            case ROOK -> "♜";
            case QUEEN -> "♛";
            case KING -> "♚";
        };
    }

    private String getSpriteForEmptySquare(Coordinates coordinates, boolean isHighLight) {
        return colorizeSprite("\t", Color.BLACK, Board.isSquareDark(coordinates), isHighLight);
    }

    private String getPieceSprite(Piece piece, boolean isHighLight) {
        return colorizeSprite(selectUnicodeSpriteForPiece(piece) + "\t", piece.color, Board.isSquareDark(piece.coordinates), isHighLight);
    }

    private String colorizeSprite(String sprite, Color pieceColor, boolean isSquareDark, boolean isHighlight) {
        // format = background color + font color + text
        String result = sprite;

        if (pieceColor == Color.WHITE) {
            result = ANSI_WHITE_PIECE_COLOR + result;
        } else {
            result = ANSI_BLACK_PIECE_COLOR + result;
        }

        if (isHighlight) {
            result = ANSI_HIGHLIGHTED_SQUARE_BACKGROUND + result;
        } else if (isSquareDark) {
            result = ANSI_BLACK_SQUARE_BACKGROUND + result;
        } else {
            result = ANSI_WHITE_SQUARE_BACKGROUND + result;
        }

        return result;
    }
}
