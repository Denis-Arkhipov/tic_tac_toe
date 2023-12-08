package com.denar.games.tic_tac_toe.services;

import com.denar.games.tic_tac_toe.datalayer.entities.Board;
import com.denar.games.tic_tac_toe.datalayer.entities.Cell;
import com.denar.games.tic_tac_toe.datalayer.entities.Move;
import com.denar.games.tic_tac_toe.datalayer.enums.CellValues;
import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import com.denar.games.tic_tac_toe.datalayer.repository.BoardRepository;
import com.denar.games.tic_tac_toe.datalayer.repository.MoveRepository;
import com.denar.games.tic_tac_toe.dto.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameProcessor {
    private static final int USER_INITIATOR = 0;
    private static final int PC_INITIATOR = 1;
    private static final int UPPERBOUND = 10;
    private static final int NUMBER_CELLS = 9;
    private final BoardRepository boardRepository;
    private final MoveRepository moveRepository;
    private final Random random = new Random();

    public GameDto newGame(int initiator) {
        Board board = new Board();

        if (initiator == PC_INITIATOR) {
            int cellIndex = makeMovePC(board);
            moveRepository.save(new Move(PC_INITIATOR, board, board.getCells().get(cellIndex)));
        }
        return new GameDto(board.getKey(), board, board.getStatus());
    }

    public int makeMovePC(Board board) {
        int cellIndex = random.nextInt(UPPERBOUND);
        Map<Integer, Cell> cells = board.getCells();
        Set<Integer> keys = cells.keySet();

        while (keys.contains(cellIndex)) {
            cellIndex = random.nextInt(UPPERBOUND);
        }

        cells.put(cellIndex, new Cell(cellIndex, CellValues.O));
        return cellIndex;
    }

    public GameDto processUserMove(String key, int cellIndex) {
        Board board = boardRepository.findByKey(UUID.fromString(key));
        Map<Integer, Cell> cells = board.getCells();
        Set<Integer> keys = cells.keySet();

        if (!board.getStatus().equals(GameStatus.GAME) || cells.size() >= NUMBER_CELLS) {
            throw new IllegalArgumentException("Game Over");
        }

        if (keys.contains(cellIndex)) {
            throw new IllegalArgumentException("Невозможно сделать ход, ячейка занята.");
        }

        cells.put(cellIndex, new Cell(cellIndex, CellValues.X));
        board.setStatus(getStatus(board.getCells()));
        moveRepository.save(new Move(USER_INITIATOR, board, board.getCells().get(cellIndex)));

        if (!board.getStatus().equals(GameStatus.GAME)) {
            cellIndex = makeMovePC(board);
            board.setStatus(getStatus(board.getCells()));
            moveRepository.save(new Move(PC_INITIATOR, board, board.getCells().get(cellIndex)));
        }
        return new GameDto(board.getKey(), board, board.getStatus());
    }

    private GameStatus getStatus(Map<Integer, Cell> cells) {
        GameStatus status = GameStatus.GAME;

        if (check(cells.get(0), cells.get(1), cells.get(2))
                || check(cells.get(0), cells.get(3), cells.get(6))
                || check(cells.get(0), cells.get(4), cells.get(8))) {
            status = getWiner(cells.get(0));
        } else if (check(cells.get(2), cells.get(5), cells.get(8))
                || check(cells.get(2), cells.get(4), cells.get(6))) {
            status = getWiner(cells.get(2));
        } else if (check(cells.get(3), cells.get(4), cells.get(5))) {
            status = getWiner(cells.get(3));
        } else if (check(cells.get(6), cells.get(7), cells.get(8))) {
            status = getWiner(cells.get(6));
        } else if (check(cells.get(1), cells.get(4), cells.get(7))) {
            status = getWiner(cells.get(1));
        }
        return status;
    }

    private boolean check(Cell cell1, Cell cell2, Cell cell3) {
        return Objects.equals(cell1.getCellValue(), cell2.getCellValue())
                && Objects.equals(cell2.getCellValue(), cell3.getCellValue());
    }

    private GameStatus getWiner(Cell cell) {
        GameStatus status = GameStatus.GAME;

        if (cell != null) {
            status = switch (cell.getCellValue()) {
                case X -> GameStatus.USER_WIN;
                case O -> GameStatus.PC_WIN;
            };
        }
        return status;
    }
}
