package com.denar.games.tic_tac_toe.services;

import com.denar.games.tic_tac_toe.datalayer.entities.Board;
import com.denar.games.tic_tac_toe.datalayer.entities.BoardEntity;
import com.denar.games.tic_tac_toe.datalayer.entities.Game;
import com.denar.games.tic_tac_toe.datalayer.entities.Move;
import com.denar.games.tic_tac_toe.datalayer.enums.CellValues;
import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import com.denar.games.tic_tac_toe.datalayer.repository.BoardRepository;
import com.denar.games.tic_tac_toe.datalayer.repository.GameRepository;
import com.denar.games.tic_tac_toe.datalayer.repository.MoveRepository;
import com.denar.games.tic_tac_toe.dto.BoardDto;
import com.denar.games.tic_tac_toe.dto.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameProcessor {
    private static final int USER_INITIATOR = 0;
    private static final int PC_INITIATOR = 1;
    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;
    private final GameMapper gameMapper;
    private final Random random = new Random();

    public GameDto newGame(int initiator) {
        Game game = gameRepository.save(new Game());
        BoardEntity board = game.getBoard();

        if (initiator == PC_INITIATOR) {
            String[] cells = gameMapper.toArray(board);
            int cellIndex = makeMovePC(cells);
            gameMapper.setBoardCells(cells, board);
            moveRepository.save(new Move(USER_INITIATOR, cellIndex, CellValues.O, board));
        }
        return new GameDto(game.getId(), board, game.getStatus());
    }

    public int makeMovePC(String[] cells) {
        int upperbound = 10;
        int cellIndex = random.nextInt(upperbound);

        do {
            if (cells[cellIndex] == null) {
                cells[cellIndex] = CellValues.O.name();
            } else {
                cellIndex = random.nextInt(upperbound);
            }
        } while (cells[cellIndex] != null);
        return cellIndex;
    }

    public GameDto processUserMove(String gameId, int cellIndex) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElse(null);

        if (game == null || game.getStatus() != null) {
            throw new IllegalArgumentException("Game Over");
        }

        BoardEntity board = game.getBoard();
        String[] cells = gameMapper.toArray(board);

        if (cells[cellIndex] == null) {
            cells[cellIndex] = CellValues.X.name();
            gameMapper.setBoardCells(cells, board);

            moveRepository.save(new Move(USER_INITIATOR, cellIndex, CellValues.X, board));
        } else {
            throw new IllegalArgumentException("Невозможно сделать ход, ячейка занята.");
        }

        if (getStatus(cells) == null) {
            cellIndex = makeMovePC(cells);
            gameMapper.setBoardCells(cells, board);
            moveRepository.save(new Move(USER_INITIATOR, cellIndex, CellValues.X, board));
        }
        return new GameDto(game.getId(), board, getStatus(cells));
    }

    private GameStatus getStatus(String[] cells) {
        GameStatus status = null;

        if (check(cells[0], cells[1], cells[2])
                || check(cells[0], cells[3], cells[6])
                || check(cells[0], cells[4], cells[8])) {
            status = getWiner(cells[0]);
        } else if (check(cells[2], cells[5], cells[8])
                || check(cells[2], cells[4], cells[6])) {
            status = getWiner(cells[2]);
        } else if (check(cells[3], cells[4], cells[5])) {
            status = getWiner(cells[3]);
        } else if (check(cells[6], cells[7], cells[8])) {
            status = getWiner(cells[6]);
        } else if (check(cells[1], cells[4], cells[7])) {
            status = getWiner(cells[1]);
        }

//        if (check(cells[0], cells[1], cells[2])) {
//            status = getWiner(cells[0]);
//        } else if (check(cells[3], cells[4], cells[5])) {
//            status = getWiner(cells[3]);
//        } else if (check(cells[6], cells[7], cells[8])) {
//            status = getWiner(cells[6]);
//        } else if (check(cells[0], cells[3], cells[6])) {
//            status = getWiner(cells[0]);
//        } else if (check(cells[1], cells[4], cells[7])) {
//            status = getWiner(cells[1]);
//        } else if (check(cells[2], cells[5], cells[8])) {
//            status = getWiner(cells[2]);
//        } else if (check(cells[0], cells[4], cells[8])) {
//            status = getWiner(cells[0]);
//        } else if (check(cells[2], cells[4], cells[6])) {
//            status = getWiner(cells[2]);
//        }

//                if (check(cells[0], cells[1], cells[2]))
//                || check(cells[3], cells[4], cells[5])
//                || check(cells[6], cells[7], cells[8])
//                || check(cells[0], cells[3], cells[6])
//                || check(cells[1], cells[4], cells[7])
//                || check(cells[2], cells[5], cells[8])
//                || check(cells[0], cells[4], cells[8])
//                || check(cells[2], cells[4], cells[6]);
        return status;
    }

    private boolean check(String cell1, String cell2, String cell3) {
        return Objects.equals(cell1, cell2) && Objects.equals(cell2, cell3);
    }

    private GameStatus getWiner(String cell) {
        GameStatus status = null;

        if (cell != null) {
            status = switch (CellValues.valueOf(cell)) {
                case X -> GameStatus.USER_WIN;
                case O -> GameStatus.PC_WIN;
            };
        }
        return status;
    }
}



//package com.denar.games.tic_tac_toe.services;
//
//import com.denar.games.tic_tac_toe.datalayer.entities.Board;
//import com.denar.games.tic_tac_toe.datalayer.entities.Game;
//import com.denar.games.tic_tac_toe.datalayer.enums.CellValues;
//import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
//import com.denar.games.tic_tac_toe.datalayer.repository.BoardRepository;
//import com.denar.games.tic_tac_toe.dto.BoardDto;
//import com.denar.games.tic_tac_toe.dto.GameDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor
//public class GameProcessor {
//    private static final int USER_INITIATOR = 0;
//    private static final int PC_INITIATOR = 1;
//    private final BoardRepository repository;
//    private final BoardMapper mapper;
//    private final Random random = new Random();
//
//    public GameDto newGame(int initiator) {
//        GameDto gameDto = new GameDto();
//        BoardDto boardDto = gameDto.getBoard();
//        Board board = new Board();
//
//        if (initiator == PC_INITIATOR) {
//            makeMovePC(boardDto);
//        }
//        board.setGameId(gameDto.getGameId());
//        repository.save(mapper.toEntity(boardDto, board));
//        return gameDto;
//    }
//
//    public void makeMovePC(BoardDto boardDto) {
//        String[] cells = boardDto.getCells();
//        int upperbound = 10;
//        int cellIndex = random.nextInt(upperbound);
//
//        do {
//            if (cells[cellIndex] == null) {
//                cells[cellIndex] = CellValues.O.name();
//            } else {
//                cellIndex = random.nextInt(upperbound);
//            }
//        } while (cells[cellIndex] != null);
//
////        for (int i = 0; i <= cells.length; i++) {
////            if (cells[i] == null) {
////                cells[i] = CellValues.O.name();
////                break;
////            }
////        }
//    }
//
//    public GameDto processUserMove(String gameId, int cellIndex) throws IllegalArgumentException {
//        Board board = repository.findByGameId(gameId);
//        BoardDto boardDto = mapper.toDto(board);
//        String[] cells = boardDto.getCells();
//        String cell = cells[cellIndex];
//
//        if (cell == null) {
//            cells[cellIndex] = CellValues.X.name();
//            repository.save(mapper.toEntity(boardDto, board));
//        } else {
//            throw new IllegalArgumentException("Невозможно сделать ход, ячейка занята.");
//        }
//
//        if (getStatus(boardDto) == null) {
//            makeMovePC(boardDto);
//            repository.save(mapper.toEntity(boardDto, board));
//        }
//
//        // Зачем создаём новую игру при каждом ходе
//        GameDto gameDto = new GameDto();
//        gameDto.setGameId(gameId);
//        gameDto.setBoard(boardDto);
//        gameDto.setStatus(getStatus(boardDto));
//        return gameDto;
//    }
//
//    private GameStatus getStatus(BoardDto board) {
//        String[] cells = board.getCells();
//        GameStatus status = null;
//
//        if (check(cells[0], cells[1], cells[2])) {
//            status = getWiner(cells[0]);
//        } else if (check(cells[3], cells[4], cells[5])) {
//            status = getWiner(cells[3]);
//        } else if (check(cells[6], cells[7], cells[8])) {
//            status = getWiner(cells[6]);
//        } else if (check(cells[0], cells[3], cells[6])) {
//            status = getWiner(cells[0]);
//        } else if (check(cells[1], cells[4], cells[7])) {
//            status = getWiner(cells[1]);
//        } else if (check(cells[2], cells[5], cells[8])) {
//            status = getWiner(cells[2]);
//        } else if (check(cells[0], cells[4], cells[8])) {
//            status = getWiner(cells[0]);
//        } else if (check(cells[2], cells[4], cells[6])) {
//            status = getWiner(cells[2]);
//        }
//
////                if (check(cells[0], cells[1], cells[2]))
////                || check(cells[3], cells[4], cells[5])
////                || check(cells[6], cells[7], cells[8])
////                || check(cells[0], cells[3], cells[6])
////                || check(cells[1], cells[4], cells[7])
////                || check(cells[2], cells[5], cells[8])
////                || check(cells[0], cells[4], cells[8])
////                || check(cells[2], cells[4], cells[6]);
//        return status;
//    }
//
//    private boolean check(String cell1, String cell2, String cell3) {
//        return Objects.equals(cell1, cell2) && Objects.equals(cell2, cell3);
//    }
//
//    private GameStatus getWiner(String cell) {
//        GameStatus status = null;
//
//        if (cell != null) {
//            status = switch (CellValues.valueOf(cell)) {
//                case X -> GameStatus.USER_WIN;
//                case O -> GameStatus.PC_WIN;
//            };
//        }
//        return status;
//    }
//}
