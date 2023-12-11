import com.chess.board.Move;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.File;
import com.chess.database.Database;
import org.junit.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DatabaseTest {

    private final Database dataBase = new Database();


    @Test
    public void lastIdInTableTest() {
        Assert.assertEquals(8, dataBase.getLastId());
        Assert.assertNotEquals(7, dataBase.getLastId());
    }

    @Test
    public void insertSqlTest() {
        int id = dataBase.getLastId();
        addElementForTest();
        Assert.assertEquals(id + 1, dataBase.getLastId());
        dataBase.deleteData(dataBase.getLastId());
    }

    @Test
    public void selectSqlTest() {
        List<String> testCase = new ArrayList<>();
        testCase.add("2023-11-06T00:40:09.854138+03:00[Europe/Moscow]");
        testCase.add("2023-11-06T00:41:21.374382500+03:00[Europe/Moscow]");
        testCase.add("White");
        testCase.add("[E2 E4, E7 E5, F1 C4, A7 A6, D1 H5, A6 A5, H5 F7]");

        List<String> result = dataBase.getSelectSql(5);
        Assert.assertEquals(testCase, result);
    }

    @Test
    public void getMovesTest() {
        addElementForTest();
        List<Move> testCase = new LinkedList<>();
        testCase.add(new Move(new Coordinates(File.B, 2), new Coordinates(File.B, 4)));
        String testCaseString = Arrays.toString(new List[]{testCase});
        String data = Arrays.toString(new List[]{dataBase.getMoves(dataBase.getLastId())});
        Assert.assertEquals(testCaseString, data);
        dataBase.deleteData(dataBase.getLastId());
    }

    @Test
    public void deleteDataTest() {
        addElementForTest();
        int id = dataBase.getLastId();
        dataBase.deleteData(id);
        Assert.assertNotEquals(id, dataBase.getLastId());
    }

    private void addElementForTest() {
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(new Coordinates(File.B, 2), new Coordinates(File.B, 4)));
        dataBase.makeInsertSql(ZonedDateTime.now(), ZonedDateTime.now(), "Black", moves);
    }
}
