package com.chess.database;
import com.chess.board.Move;
import com.chess.coordinates.Coordinates;
import com.chess.coordinates.File;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;
public class Database {

    private final Connection connection;

    public Database() {
        try {
            DatabaseProperties properties = PropertiesFactory.getProperties();
            connection = DriverManager.getConnection(
                    properties.getUrl(),
                    properties.getLogin(),
                    properties.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void makeInsertSql(ZonedDateTime start, ZonedDateTime end, String winner, List<Move> moves) {
        try (Statement statement = connection.createStatement()) {
            String info = "'" + start + "', '" + end + "', '" + winner + "', '" + moves + "'";
            String sql = "insert into history(Time_start, Time_end, Winner, Moves) values (" + info + ")";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Move> getMoves(int id) {
        List<String> info =  getSelectSql(id);
        List<String> moves = List.of(info.get(3).split(" "));

        List<Move> result = new LinkedList<>();

        for (int i = 0; i < moves.size(); i += 2) {
            int index = 0;

            String firstMove = moves.get(i);
            String secondMove = moves.get(i + 1);

            while (firstMove.charAt(index) == '[') {
                index++;
            }

            File file = File.valueOf((firstMove.charAt(index) + "").toUpperCase());
            int rank = Integer.parseInt(firstMove.charAt(index + 1) + "");

            Coordinates from = new Coordinates(file, rank);

            file = File.valueOf((secondMove.charAt(0) + "").toUpperCase());
            rank = Integer.parseInt(secondMove.charAt(1) + "");

            Coordinates to = new Coordinates(file, rank);

            result.add(new Move(from, to));
        }

        return result;
    }

    public List<String> getSelectSql(int id) {
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM history where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            List<String> data = new ArrayList<>();

            while (resultSet.next()) {
                data.add(resultSet.getString("time_start"));
                data.add(resultSet.getString("time_end"));
                data.add(resultSet.getString("winner"));
                data.add(resultSet.getString("moves"));
            }

            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteData(int id) {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM history where id =" + id;
            statement.executeUpdate(sql);

            this.updateLastId(this.getLastId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateLastId(int id) {
        try (Statement statement = connection.createStatement()) {
            String sql = "ALTER SEQUENCE history_id_seq RESTART with " + (id + 1);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastId() {
        try (Statement statement = connection.createStatement()) {
            String sql = """
                    SELECT id
                    FROM  history
                    WHERE ID = (SELECT MAX(id)  FROM history)
                    """;

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}

