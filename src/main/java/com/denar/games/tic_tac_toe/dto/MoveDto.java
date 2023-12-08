package com.denar.games.tic_tac_toe.dto;

import com.denar.games.tic_tac_toe.datalayer.enums.CellValues;
import lombok.Data;

@Data
public class MoveDto {
    private String boardKey;
    private Integer cellIndex;
    private CellValues cellValues;
}
