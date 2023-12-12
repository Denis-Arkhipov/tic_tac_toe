package com.denar.games.tic_tac_toe.utils;

import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;

import java.util.Set;

public class GameUtils {

    private GameUtils() {
    }

    public static final int LIMIT_MOVES = 2;

    public static void checkGameOver(GameStatus gameStatus) {
        if (!gameStatus.equals(GameStatus.GAME)) {
            throw new IllegalArgumentException(String.format("Game Over: %s", gameStatus.name()));
        }
    }

    public static void checkValidMove(Set<Integer> keys, int cellIndex) {
        if (keys.contains(cellIndex)) {
            throw new IllegalArgumentException(String.format("Невозможно сделать ход, ячейка %d занята.", cellIndex));
        }
    }

    public static void checkDeleteMove(int numberMoves) {
        if (numberMoves < LIMIT_MOVES) {
            throw new IllegalStateException("Невозможно отменить ход.");
        }
    }
}
