package com.chess.game;

import com.chess.board.Board;
import com.chess.board.BoardConsoleRenderer;
import com.chess.board.Move;
import com.chess.coordinates.Color;

import java.util.List;


public class Game {

    private final Board board;

    private final BoardConsoleRenderer render = new BoardConsoleRenderer();

    private final List<GameStateChecker> checkers = List.of(
            new StaleMateGameChecker(),
            new CheckmateGameStateChecker()
    );


    public Game(Board board) {
        this.board = board;
    }

    public void gameLoop() {
        Color colorToMove = Color.WHITE;

        GameState state = determineGameState(board, colorToMove);

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
            // pass move
            colorToMove = colorToMove.opposite();

            state = determineGameState(board, colorToMove);
        }

        render.render(board);
        System.out.println("Game ending with state = " + state);
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
