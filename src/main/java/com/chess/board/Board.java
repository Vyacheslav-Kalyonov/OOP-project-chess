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
    private final String startingFen;
    private HashMap<Coordinates, Piece> pieces = new HashMap<>(); // Для доски. Ключ - координата, значения - фигура

    private List<Move> moves = new ArrayList<>();

    public Board(String startingFen) {
        this.startingFen = startingFen;
    }

    public static boolean isSquareDark(Coordinates coordinates) {
        return (coordinates.getFile().ordinal() + 1 + coordinates.getRank()) % 2 == 0;
    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        return !pieces.containsKey(coordinates);
    }

    public String getStartingFen() {
        return startingFen;
    }

    public HashMap<Coordinates, Piece> getPieces() {
        return pieces;
    }

    public List<Move> getMoves() {
        return moves;
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
            if (piece.getColor() == color) {
                result.add(piece);
            }
        }

        return result;
    }

    public void makeMove(Move move) {
        Piece piece = getPiece(move.getFrom());

        removePiece(move.getFrom());
        setPiece(move.getTo(), piece);

        moves.add(move);
    }

    public void checkPawnPosition(Move move) {
        Piece piece = getPiece(move.getTo());

        if ((getPiece(move.getTo()) instanceof Pawn) && (move.getTo().getRank() == 1 || move.getTo().getRank() == 8)) {
            // метод введения фигуры
            if (getPiece(move.getTo()) instanceof Pawn) {
                String namePiece = InputCoordinates.inputPieceForSwapPawn();
                setPieceForSwapPawn(namePiece, move.getTo(), piece.getColor());
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
        piece.setCoordinates(coordinates);
        pieces.put(coordinates, piece);
    }
}
