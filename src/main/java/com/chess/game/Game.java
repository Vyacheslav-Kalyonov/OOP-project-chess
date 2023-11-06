package com.chess.game;

import com.chess.database.DataBase;
import com.chess.board.Board;
import com.chess.board.BoardConsoleRenderer;
import com.chess.board.Move;
import com.chess.coordinates.Color;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Board board;

    private final DataBase dataBase = new DataBase();

    private final BoardConsoleRenderer render = new BoardConsoleRenderer();
    private final Scanner scanner = new Scanner(System.in);

    private final List<GameStateChecker> checkers = List.of(
            new StaleMateGameChecker(),
            new CheckmateGameStateChecker()
    );


    public Game(Board board) {
        this.board = board;
    }

    public void playOrCheckHistory() {
        System.out.println("Write: PLAY or HISTORY");
        String check = scanner.nextLine();

        check = check.toUpperCase();
        while (true) {
            switch (check) {
                case "PLAY": {
                    gameLoop();
                    return;
                }
                case "HISTORY": {
                    checkHistory();
                    return;
                }

                default: {
                    System.out.println("Wrong data, please retype PLAY or HISTORY");
                    check = scanner.nextLine().toUpperCase();
                }
            }
        }
    }

    public void checkHistory() {
        System.out.println("Write the ID of the party");
        int id = scanner.nextInt();

        while (true) {
            if (id > dataBase.getLastId() || id <= 0) {
                System.out.println("Wrong data : there is no such id");
                System.out.println("Retype id");
                id = scanner.nextInt();
                continue;
            }

            break;
        }

        List<Move> moves = dataBase.getMoves(id);
        System.out.println("Moves in party : " + moves);
        System.out.println("Do you want to reproduce the party? (yes, no)");
        Scanner scanner1 = new Scanner(System.in);
        String check = scanner1.nextLine();

        check = check.toLowerCase();

        switch (check) {
            case "yes": {
                render.render(board);

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                for (Move move : moves) {
                    board.makeMove(move);
                    render.render(board);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            case "no": {
                System.out.println("Program end");
            }
        }
    }
    public void gameLoop() {
        Color colorToMove = Color.WHITE;

        GameState state = determineGameState(board, colorToMove);

        ZonedDateTime start = ZonedDateTime.now();

        while (state == GameState.ONGOING) {
            render.render(board);

            if (colorToMove == Color.WHITE) {
                System.out.println("White to move");
            } else {
                System.out.println("Black to move");
            }

            Move move = InputCoordinates.inputMove(board, colorToMove, render);


            // make move
            board.makeMove(move);
            board.checkPawnPosition(move);

            // pass move
            colorToMove = colorToMove.opposite();

            state = determineGameState(board, colorToMove);
        }

        render.render(board);
        System.out.println("Game ending with state = " + state);

        ZonedDateTime end = ZonedDateTime.now();
        String winner = state.getWinner(state);

        dataBase.save(start, end, winner, board.moves);
        System.out.println("Game id : " + dataBase.getLastId());
    }

    private GameState determineGameState(Board board, Color color) {
        for (GameStateChecker checker : checkers) {
            GameState state = checker.check(board, color);

            if (state != GameState.ONGOING) {
                return state;
            }
        }
        return GameState.ONGOING;
    }
}
