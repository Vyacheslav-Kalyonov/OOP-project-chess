package com.chess.game;

import com.chess.board.*;
import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.File;
import com.chess.piece.*;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

public class InputCoordinates {

    private static final Scanner scanner = new Scanner(System.in);

    public static Coordinates input() {
        while (true) {
            System.out.println("Please enter coordinates (ex. a1)");
            String line = scanner.nextLine();

            if (line.length() != 2) {
                System.out.println("Invalid format");
                continue;
            }

            char fileChar = line.charAt(0);
            char rankChar = line.charAt(1);

            if (!Character.isLetter(fileChar)) {
                System.out.println("Invalid format");
                continue;
            }

            if (!Character.isDigit(rankChar)) {
                System.out.println("Invalid format");
                continue;
            }

            int rank = Character.getNumericValue(rankChar);
            if (rank < 1 || rank > 8) {
                System.out.println("Invalid format");
                continue;
            }

            File file = File.fromChar(fileChar);
            if (file == null) {
                System.out.println("Invalid format");
                continue;
            }

            return new Coordinates(file, rank);
        }
    }

    public static Coordinates inputPieceCoordinatesForColor(Color color, Board board) {
        while (true) {
            System.out.println("Enter coordinates for a piece to move");

            Coordinates coordinates = input();

            if (board.isSquareEmpty(coordinates)) {
                System.out.println("Empty square");
                continue;
            }

            Piece piece = board.getPiece(coordinates);
            if (piece.getColor() != color) {
                System.out.println("Wrong color");
                continue;
            }

            Set<Coordinates> availableMovesSquares = piece.getAvailableMoveSquares(board);
            if (availableMovesSquares.isEmpty()) {
                System.out.println("Blocked piece");
                continue;
            }

            return coordinates;
        }
    }

    public static Coordinates inputAvailableSquare(Set<Coordinates> coordinates) {
        while (true) {
            System.out.println("Enter your move for selected move");
            Coordinates input = input();

            if (!coordinates.contains(input)) {
                System.out.println("Non-available square");
                continue;
            }

            return input;
        }
    }

    public static Move inputMove(Board board, Color color, BoardConsoleRenderer render) {
        while (true) {
            // input
            Coordinates sourceCoordinates = InputCoordinates.inputPieceCoordinatesForColor(color, board);

            Piece piece = board.getPiece(sourceCoordinates);
            Set<Coordinates> availableMoveSquares = piece.getAvailableMoveSquares(board);
            render.render(board, piece);
            Coordinates targetCoordinates = InputCoordinates.inputAvailableSquare(availableMoveSquares);

            Move move = new Move(sourceCoordinates, targetCoordinates);


            if (validateIfKingInCheckAfterMove(board, color, move)) {
                System.out.println("Your king is under attack!");
                continue;
            }

            return move;
        }


    }

    public static String inputPieceForSwapPawn() {
        System.out.println("Input piece for swap (Queen, Rook, Bishop, Knight)");
        String namePiece = scanner.nextLine();

        while (true) {
            if (namePiece.equals("Queen") || namePiece.equals("Rook") || namePiece.equals("Bishop") || namePiece.equals("Knight")) {
                return namePiece;
            } else {
                System.out.println("Wrong piece");
                namePiece = scanner.nextLine();
            }
        }
    }

    private static boolean validateIfKingInCheckAfterMove(Board board, Color color, Move move) {
        Board copy = (new BoardFactory().copy(board));
        copy.makeMove(move);

        Piece king = copy.getPiecesByColor(color).stream().filter(piece -> piece instanceof King).findFirst().get();
        copy.isSquareAttackedByColor(king.getCoordinates(), color.opposite());

        return copy.isSquareAttackedByColor(king.getCoordinates(), color.opposite());
    }
}
