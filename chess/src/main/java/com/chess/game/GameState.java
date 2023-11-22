package com.chess.game;

public enum GameState {
    ONGOING, STALEMATE, CHECKMATE_TO_WHITE_KING, CHECKMATE_TO_BLACK_KING;

    public String getWinner(GameState state) {
        switch (state) {
            case STALEMATE -> {
                return "Draw";
            }
            case CHECKMATE_TO_BLACK_KING -> {
                return "White";
            }
            case CHECKMATE_TO_WHITE_KING -> {
                return "Black";
            }

            default -> {
                return null;
            }
        }
    }
}
