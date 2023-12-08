package com.denar.games.tic_tac_toe.services;

import com.denar.games.tic_tac_toe.datalayer.entities.Board;
import com.denar.games.tic_tac_toe.datalayer.entities.BoardEntity;
import com.denar.games.tic_tac_toe.datalayer.entities.Game;
import com.denar.games.tic_tac_toe.datalayer.entities.Move;
import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import com.denar.games.tic_tac_toe.dto.BoardDto;
import com.denar.games.tic_tac_toe.dto.GameDto;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    public BoardDto toBoardDto(Board entity) {
        BoardDto boardDto = new BoardDto();
        String[] cells = boardDto.getCells();
        cells[0] = entity.getCell0();
        cells[1] = entity.getCell1();
        cells[2] = entity.getCell2();
        cells[3] = entity.getCell3();
        cells[4] = entity.getCell4();
        cells[5] = entity.getCell5();
        cells[6] = entity.getCell6();
        cells[7] = entity.getCell7();
        cells[8] = entity.getCell8();

        boardDto.setCells(cells);
        return boardDto;
    }

    public Board toBoardEntity(BoardDto boardDto, Board entity) {
        String[] cells = boardDto.getCells();
        entity.setCell0(cells[0]);
        entity.setCell1(cells[1]);
        entity.setCell2(cells[2]);
        entity.setCell3(cells[3]);
        entity.setCell4(cells[4]);
        entity.setCell5(cells[5]);
        entity.setCell6(cells[6]);
        entity.setCell7(cells[7]);
        entity.setCell8(cells[8]);
        return entity;
    }

    public void setBoardCells(String[] cells, BoardEntity entity) {
        entity.setCell0(cells[0]);
        entity.setCell1(cells[1]);
        entity.setCell2(cells[2]);
        entity.setCell3(cells[3]);
        entity.setCell4(cells[4]);
        entity.setCell5(cells[5]);
        entity.setCell6(cells[6]);
        entity.setCell7(cells[7]);
        entity.setCell8(cells[8]);
    }

//    public GameDto toGameDto(Game game) {
//        GameDto gameDto = new GameDto();
//        gameDto.setGameId(game.getGameId().toString());
//        gameDto.setBoard(game.getBoard());
//        gameDto.setStatus(game.getStatus());
//        return gameDto;
//    }

    public String[] toArray(BoardEntity entity) {
        return new String[]{
                entity.getCell0(),
                entity.getCell1(),
                entity.getCell2(),
                entity.getCell3(),
                entity.getCell4(),
                entity.getCell5(),
                entity.getCell6(),
                entity.getCell7(),
                entity.getCell8()
        };
    }

//    public Board toEntity(BoardDto boardDto, String gameId) {
//        Board entity = new Board();
//        String[] cells = boardDto.getCells();
//        entity.setCell0(cells[0]);
//        entity.setCell1(cells[1]);
//        entity.setCell2(cells[2]);
//        entity.setCell3(cells[3]);
//        entity.setCell4(cells[4]);
//        entity.setCell5(cells[5]);
//        entity.setCell6(cells[6]);
//        entity.setCell7(cells[7]);
//        entity.setCell8(cells[8]);
//
//        entity.setGameId(gameId);
//        return entity;
//    }
}
