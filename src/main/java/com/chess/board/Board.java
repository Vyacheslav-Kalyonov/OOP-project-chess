package com.chess.board;

import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.game.InputCoordinates;
import com.chess.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Board {
    public final String startingFen;
    public HashMap<Coordinates, Piece> pieces = new HashMap<>(); // Для доски. Ключ - координата, значения - фигура

    public List<Move> moves = new ArrayList<>();

    public Board(String startingFen) {
        this.startingFen = startingFen;
    }

    public static boolean isSquareDark(Coordinates coordinates) {
        return (((coordinates.file.ordinal() + 1) + coordinates.rank) % 2) == 0;
    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        return !pieces.containsKey(coordinates);
    }

    public boolean isSquareAttackedByColor(Coordinates coordinates, Color color) {
        List<Piece> pieces = getPiecesByColor(color);

        for (Piece piece : pieces) {
            Set<Coordinates> attackedSquares = piece.getAttackedSquares(this);

            if (attackedSquares.contains(coordinates)) {
                return true;
            }
        }

        return false;
    }

    public List<Piece> getPiecesByColor(Color color) {
        List<Piece> result = new ArrayList<>();

        for (Piece piece : pieces.values()) {
            if (piece.color == color) {
                result.add(piece);
            }
        }

        return result;
    }

    public void makeMove(Move move) {
        Piece piece = getPiece(move.from);

        removePiece(move.from);
        setPiece(move.to, piece);

        moves.add(move);
    }

    public void checkPawnPosition(Move move) {
        Piece piece = getPiece(move.to);

        if ((getPiece(move.to) instanceof Pawn) && (move.to.rank == 1 || move.to.rank == 8)) {
            // метод введения фигуры
            if (getPiece(move.to) instanceof Pawn) {
                String namePiece = InputCoordinates.inputPieceForSwapPawn();
                setPieceForSwapPawn(namePiece, move.to, piece.color);
            }
        }

    }

    public Piece getPiece(Coordinates coordinates) {
        return pieces.get(coordinates);
    }

    public void setPieceForSwapPawn(String name, Coordinates coordinates, Color color) {
        removePiece(coordinates);

        switch (PieceForConsoleRender.valueOf(name.toUpperCase())) {
            case KNIGHT -> setPiece(coordinates, new Knight(color, coordinates));
            case ROOK -> setPiece(coordinates, new Rook(color, coordinates));
            case QUEEN -> setPiece(coordinates, new Queen(color, coordinates));
            case BISHOP -> setPiece(coordinates, new Bishop(color, coordinates));
        }
    }

    public void removePiece(Coordinates coordinates) {
        pieces.remove(coordinates);
    }

    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.coordinates = coordinates;
        pieces.put(coordinates, piece);
    }
}
