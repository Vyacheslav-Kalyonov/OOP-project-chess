package com.chess.piece;

import com.chess.board.Board;
import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.CoordinatesShift;

import java.util.HashSet;
import java.util.Set;

// фигура абстрактная, все остальные фигуры наследуются от нее
abstract public class Piece {

    protected Color color;

    protected Coordinates coordinates;

    public Piece(Color color, Coordinates coordinates) {
        this.color = color;
        this.coordinates = coordinates;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Color getColor() {
        return color;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    //  Все ходы фигур
    public Set<Coordinates> getAvailableMoveSquares(Board board) {
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift: getPieceMoves()) {
            if (coordinates.canShift(shift)) {
                Coordinates newCoordinates = coordinates.shift(shift);

                if (isSquareAvailableForMove(newCoordinates, board)) {
                    result.add(newCoordinates);
                }
            }
        }

        return result;
    }

    // проверка на возможность хода (проверяется пустота клетки или разный цвет фигур)
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        return board.isSquareEmpty(coordinates) || (board.getPiece(coordinates).color != color);
    }

    // возможные ходы фигур
    protected  abstract Set<CoordinatesShift> getPieceMoves();

    protected Set<CoordinatesShift> getPieceAttacks() {
        return getPieceMoves();
    }

    public Set<Coordinates> getAttackedSquares(Board board) {
        Set<CoordinatesShift> pieceAttacks = getPieceAttacks();
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift pieceAttack : pieceAttacks) {
            if (coordinates.canShift(pieceAttack)) {
                Coordinates shiftedCoordinates = coordinates.shift(pieceAttack);

                if (isSquareAvailableForAttack(shiftedCoordinates, board)) {
                    result.add(shiftedCoordinates);
                }
            }
        }

        return result;
    }

    protected boolean isSquareAvailableForAttack(Coordinates shiftedCoordinates, Board board) {
        return true;
    }
}
