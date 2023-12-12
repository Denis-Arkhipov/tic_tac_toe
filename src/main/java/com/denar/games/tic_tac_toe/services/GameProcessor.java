package com.denar.games.tic_tac_toe.services;

import com.denar.games.tic_tac_toe.datalayer.entities.Board;
import com.denar.games.tic_tac_toe.datalayer.entities.Cell;
import com.denar.games.tic_tac_toe.datalayer.entities.Move;
import com.denar.games.tic_tac_toe.datalayer.enums.CellValues;
import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import com.denar.games.tic_tac_toe.datalayer.repository.BoardRepository;
import com.denar.games.tic_tac_toe.datalayer.repository.CellRepository;
import com.denar.games.tic_tac_toe.datalayer.repository.MoveRepository;
import com.denar.games.tic_tac_toe.dto.GameDto;
import com.denar.games.tic_tac_toe.utils.GameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameProcessor {
    private static final int USER_INITIATOR = 1;
    private static final int PC_INITIATOR = 0;
    private static final int UPPERBOUND = 10;
    private static final int NUMBER_CELLS = 9;
    private final BoardRepository boardRepository;
    private final CellRepository cellRepository;
    private final MoveRepository moveRepository;
    private final Random random = new Random();

    public GameDto newGame(int initiator) {
        Board board = boardRepository.save(new Board());

        if (initiator == PC_INITIATOR) {
            int cellIndex = getCellIndexPC(board.getCells());
            makeMove(board, cellIndex, PC_INITIATOR);
        }
        return new GameDto(board.getId(), board, board.getStatus());
    }

    public GameDto getBoard(String boardId) {
        Board board = boardRepository.findById(UUID.fromString(boardId)).orElseThrow();
        return new GameDto(board.getId(), board, board.getStatus());
    }

    public GameDto processUserMove(String boardId, int cellIndex) {
        Board board = boardRepository.findById(UUID.fromString(boardId)).orElseThrow();
        makeMove(board, cellIndex, USER_INITIATOR);

        if (board.getStatus().equals(GameStatus.GAME)) {
            int cellIndexPC = getCellIndexPC(board.getCells());
            makeMove(board, cellIndexPC, PC_INITIATOR);
        }
        return new GameDto(board.getId(), board, board.getStatus());
    }

    public GameDto cancelLastMove(String boardId) {
        List<Move> move = moveRepository.findTop2ByBoardIdOrderByDateDesc(UUID.fromString(boardId));

        GameUtils.checkDeleteMove(move.size());

        move.forEach(moveRepository::delete);
        Board board = boardRepository.findById(UUID.fromString(boardId)).orElseThrow();
        return new GameDto(board.getId(), board, board.getStatus());
    }

    private void makeMove(Board board, int cellIndex, int initiator) {
        GameUtils.checkGameOver(board.getStatus());

        Map<Integer, Cell> cells = board.getCells();
        Set<Integer> keys = cells.keySet();

        GameUtils.checkValidMove(keys, cellIndex);

        Cell cell = cellRepository.save(new Cell(cellIndex, getCellValue(initiator)));
        cells.put(cellIndex, cell);
        board.setStatus(getGameStatus(cells));
        moveRepository.save(new Move(initiator, board, cell));
    }

    private int getCellIndexPC(Map<Integer, Cell> cells) {
        Set<Integer> keys = cells.keySet();
        int cellIndex = random.nextInt(UPPERBOUND);

        while (keys.contains(cellIndex)) {
            cellIndex = random.nextInt(UPPERBOUND);
        }
        return cellIndex;
    }

    private GameStatus getGameStatus(Map<Integer, Cell> cells) {
        GameStatus status = GameStatus.GAME;

        if (checkWinCombination(cells.get(0), cells.get(1), cells.get(2))
                || checkWinCombination(cells.get(0), cells.get(3), cells.get(6))
                || checkWinCombination(cells.get(0), cells.get(4), cells.get(8))) {
            status = getWinner(cells.get(0));
        } else if (checkWinCombination(cells.get(2), cells.get(5), cells.get(8))
                || checkWinCombination(cells.get(2), cells.get(4), cells.get(6))) {
            status = getWinner(cells.get(2));
        } else if (checkWinCombination(cells.get(3), cells.get(4), cells.get(5))) {
            status = getWinner(cells.get(3));
        } else if (checkWinCombination(cells.get(6), cells.get(7), cells.get(8))) {
            status = getWinner(cells.get(6));
        } else if (checkWinCombination(cells.get(1), cells.get(4), cells.get(7))) {
            status = getWinner(cells.get(1));
        } else if (cells.size() >= NUMBER_CELLS) {
            status = GameStatus.DEAD_HEAT;
        }
        return status;
    }

    private boolean checkWinCombination(Cell cell1, Cell cell2, Cell cell3) {
        boolean win = false;

        if (!Objects.isNull(cell1) && !Objects.isNull(cell2) && !Objects.isNull(cell3)) {
            win = Objects.equals(cell1.getCellValue(), cell2.getCellValue())
                    && Objects.equals(cell2.getCellValue(), cell3.getCellValue());
        }
        return win;
    }

    private GameStatus getWinner(Cell cell) {
        return switch (cell.getCellValue()) {
            case X -> GameStatus.USER_WIN;
            case O -> GameStatus.PC_WIN;
        };
    }

    private CellValues getCellValue(int initiator) {
        return switch (initiator) {
            case USER_INITIATOR -> CellValues.X;
            case PC_INITIATOR -> CellValues.O;
            default -> throw new IllegalStateException("Unexpected value: " + initiator);
        };
    }
}
