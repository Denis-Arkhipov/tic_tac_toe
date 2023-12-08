package com.denar.games.tic_tac_toe.datalayer.entities;

import com.denar.games.tic_tac_toe.datalayer.enums.CellValues;
import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer initiator;
    private Integer cellIndex;
    private CellValues cellValue;
    private GameStatus gameStatus;
    private Timestamp date = Timestamp.valueOf(LocalDateTime.now());

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    public Move(Integer initiator, Integer cellIndex, CellValues cellValues, BoardEntity board) {
        this.initiator = initiator;
        this.cellIndex = cellIndex;
        this.cellValue = cellValues;
        this.board = board;
    }
}
