import com.chess.board.Board;
import com.chess.board.BoardFactory;
import com.chess.coordinates.Color;
import com.chess.game.CheckmateGameStateChecker;
import com.chess.game.GameState;
import com.chess.game.StaleMateGameChecker;
import org.junit.Assert;
import org.junit.Test;

public class CheckmateGameCheckerTest {

    private Board board;
    StaleMateGameChecker checker = new StaleMateGameChecker();
    CheckmateGameStateChecker checkerColor = new CheckmateGameStateChecker();

    @Test
    public void gameStateCheckMateToWhiteKing() {
        board = (new BoardFactory()).fromFEN("7k/8/5b2/3b4/8/8/1n6/K1r5 w - - 0 1");

        Assert.assertEquals(GameState.CHECKMATE_TO_WHITE_KING, checkerColor.check(board, Color.WHITE));
    }

    @Test
    public void gameStateCheckMateToBlackKing() {
        board = (new BoardFactory()).fromFEN("Q6k/8/7K/8/8/8/8/8 w - - 0 1");

        Assert.assertEquals(GameState.CHECKMATE_TO_BLACK_KING, checkerColor.check(board, Color.BLACK));
    }

    @Test
    public void gameStateOngoing() {
        board = (new BoardFactory()).fromFEN("Q6k/8/8/7K/8/8/8/8 w - - 0 1");

        Assert.assertEquals(GameState.ONGOING, checkerColor.check(board, Color.BLACK));
    }

    @Test
    public void gameStateCheckStalemate() {
        board = (new BoardFactory()).fromFEN("7k/4Q3/5KP1/8/2B5/8/8/8 w - - 0 1");

        Assert.assertEquals(GameState.STALEMATE, checker.check(board, Color.BLACK));
    }
}
