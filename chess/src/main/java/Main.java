import com.chess.board.Board;
import com.chess.board.BoardFactory;
import com.chess.game.Game;


public class Main {
    public static void main(String[] args) {
        Board board = (new BoardFactory()).fromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Game game = new Game(board);

        game.playOrCheckHistory();
    }
}
