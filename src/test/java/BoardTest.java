import com.chess.board.Board;
import com.chess.board.BoardFactory;
import com.chess.board.Move;
import com.chess.coordinates.Color;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.File;
import com.chess.piece.Bishop;
import com.chess.piece.King;
import com.chess.piece.Knight;
import com.chess.piece.Rook;
import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

    private Board board;

    @Test
    public void getPieceTest() {
        board = (new BoardFactory()).fromFEN("8/8/5b2/8/8/8/1n6/Kr6 w - - 0 1");

        Assert.assertTrue(board.getPiece(new Coordinates(File.A, 1)) instanceof King);
        Assert.assertTrue(board.getPiece(new Coordinates(File.B, 1)) instanceof Rook);
        Assert.assertTrue(board.getPiece(new Coordinates(File.B, 2)) instanceof Knight);
        Assert.assertTrue(board.getPiece(new Coordinates(File.F, 6)) instanceof Bishop);
    }

    @Test
    public void makeMoveTest() {
        board = (new BoardFactory()).fromFEN("8/8/5b2/8/8/8/1n6/Kr6 w - - 0 1");
        board.makeMove(new Move(new Coordinates(File.A, 1), new Coordinates(File.B, 1)));

        Assert.assertTrue(board.getPiece(new Coordinates(File.B, 1)) instanceof King);
    }

    @Test
    public void isSquareAttackedByColorTest() {
        board = (new BoardFactory()).fromFEN("8/8/5b2/8/8/8/Kn6/2r5 w - - 0 1");

        Assert.assertTrue(board.isSquareAttackedByColor(new Coordinates(File.B, 1), Color.BLACK));
        Assert.assertTrue(board.isSquareAttackedByColor(new Coordinates(File.A, 1), Color.BLACK));
        Assert.assertFalse(board.isSquareAttackedByColor(new Coordinates(File.A, 3), Color.BLACK));
    }
}
