package com.chess.board;

import com.chess.coordinates.Coordinates;
import com.chess.coordinates.File;
import com.chess.piece.PieceFactory;

public class BoardFactory {

    private final PieceFactory pieceFactory = new PieceFactory();
    // FEN - формат, по которому задается расположение фигур на доске
    // статрт позиция задается как rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
    public Board fromFEN(String fen) {
        Board board = new Board(fen);

        String[] parts = fen.split(" ");
        String piecePosition = parts[0];

        String[] fenRows = piecePosition.split("/");

        for (int i = 0; i < fenRows.length; i++) {
            String row = fenRows[i];
            int rank = 8 - i;

            int fileIndex = 0;
            for (int j = 0; j < row.length(); j++) {
                char fenChar = row.charAt(j);

                if (Character.isDigit(fenChar)) {
                    fileIndex += Character.getNumericValue(fenChar);
                } else {
                    File file = File.values()[fileIndex];
                    Coordinates coordinates = new Coordinates(file, rank);

                    board.setPiece(coordinates, pieceFactory.fromFenChar(fenChar, coordinates));
                    fileIndex++;
                }
            }
        }
        return board;
    }

    public Board copy(Board source) {
        Board clone = fromFEN(source.getStartingFen());

        for (Move move : source.getMoves()) {
            clone.makeMove(move);
        }

        return clone;
    }
}
