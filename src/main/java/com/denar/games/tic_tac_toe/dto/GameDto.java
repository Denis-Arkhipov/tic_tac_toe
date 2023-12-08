package com.denar.games.tic_tac_toe.dto;

import com.denar.games.tic_tac_toe.datalayer.entities.Board;
import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GameDto {
    private UUID gameId;
    private Board board;
    private GameStatus status;
}
