package com.chess.game;

import com.chess.coordinates.Color;
import com.chess.board.Board;

public abstract class GameStateChecker {

    public abstract GameState check (Board board, Color color);
}
