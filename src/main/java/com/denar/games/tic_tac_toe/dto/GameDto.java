package com.denar.games.tic_tac_toe.dto;

import com.denar.games.tic_tac_toe.datalayer.entities.BoardEntity;
import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GameDto {
    private UUID gameId;
    private BoardEntity board;
    private GameStatus status;
//    private String gameId = UUID.randomUUID().toString();
//    private BoardDto board = new BoardDto();
//    private GameStatus status;
}
