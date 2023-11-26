import com.chess.board.Board;
import com.chess.board.BoardConsoleRenderer;
import com.chess.board.BoardFactory;
import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.File;
import com.chess.piece.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class PiecesAvailableMovesTest {
    private Board board;
    private Piece piece;

    @Test
    public void testQueenMoves() {
        board = (new BoardFactory()).fromFEN("8/8/8/8/8/P7/1B6/Q1r5 w - - 0 1");
        // 8/8/8/8/8/P7/1B6/Q1r5 w - - 0 1
        
        Set<Coordinates> testCaseMoves = new HashSet<>();
        testCaseMoves.add(new Coordinates(File.A, 2));
        testCaseMoves.add(new Coordinates(File.B, 1));
        testCaseMoves.add(new Coordinates(File.C, 1));
        piece = new Queen(Color.WHITE, new Coordinates(File.A, 1));

        Assert.assertEquals(testCaseMoves, piece.getAvailableMoveSquares(board));
    }

    @Test
    public void testBishopMove() {
        board = (new BoardFactory()).fromFEN("8/8/1P3q2/8/3B4/4r3/1Q6/8 w - - 0 1");
        
        Set<Coordinates> testCaseMoves = new HashSet<>();
        testCaseMoves.add(new Coordinates(File.E, 3));
        testCaseMoves.add(new Coordinates(File.F, 6));
        testCaseMoves.add(new Coordinates(File.E, 5));
        testCaseMoves.add(new Coordinates(File.C, 3));
        testCaseMoves.add(new Coordinates(File.C, 5));
        piece = new Bishop(Color.WHITE, new Coordinates(File.D, 4));

        Assert.assertEquals(testCaseMoves, piece.getAvailableMoveSquares(board));
    }

    @Test
    public void testRookMoves() {
        board = (new BoardFactory()).fromFEN("8/8/8/8/1B6/8/Prn5/8 w - - 0 1");
        
        Set<Coordinates> testCaseMoves = new HashSet<>();
        testCaseMoves.add(new Coordinates(File.B, 1));
        testCaseMoves.add(new Coordinates(File.A, 2));
        testCaseMoves.add(new Coordinates(File.B, 3));
        testCaseMoves.add(new Coordinates(File.B, 4));

        piece = new Rook(Color.BLACK, new Coordinates(File.B, 2));

        Assert.assertEquals(testCaseMoves, piece.getAvailableMoveSquares(board));
    }

    @Test
    public void testKnightMoves() {
        board = (new BoardFactory()).fromFEN("8/8/8/8/1B6/8/Pr6/2n5 w - - 0 1");
        
        Set<Coordinates> testCaseMoves = new HashSet<>();
        testCaseMoves.add(new Coordinates(File.E, 2));
        testCaseMoves.add(new Coordinates(File.A, 2));
        testCaseMoves.add(new Coordinates(File.B, 3));
        testCaseMoves.add(new Coordinates(File.D, 3));

        piece = new Knight(Color.BLACK, new Coordinates(File.C, 1));

        Assert.assertEquals(testCaseMoves, piece.getAvailableMoveSquares(board));
    }

    @Test
    public void testPawnMoves() {
        board = (new BoardFactory()).fromFEN("8/8/8/8/8/1r6/P7/8 w - - 0 1");
        
        Set<Coordinates> testCaseMoves = new HashSet<>();
        testCaseMoves.add(new Coordinates(File.A, 4));
        testCaseMoves.add(new Coordinates(File.A, 3));
        testCaseMoves.add(new Coordinates(File.B, 3));

        piece = new Pawn(Color.WHITE, new Coordinates(File.A, 2));

        Assert.assertEquals(testCaseMoves, piece.getAvailableMoveSquares(board));
    }

    @Test
    public void testKingMoves() {
        board = (new BoardFactory()).fromFEN("8/8/5b2/8/8/8/1n6/Kr6 w - - 0 1");
        
        Set<Coordinates> testCaseMoves = new HashSet<>();
        testCaseMoves.add(new Coordinates(File.A, 2));
        testCaseMoves.add(new Coordinates(File.B, 1));

        piece = new King(Color.WHITE, new Coordinates(File.A, 1));

        Assert.assertEquals(testCaseMoves, piece.getAvailableMoveSquares(board));
    }
}
