package com.denar.games.tic_tac_toe.datalayer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "cell_id")
    private Cell cell;

    public Move(Integer initiator, Board board, Cell cell) {
        this.initiator = initiator;
        this.board = board;
        this.cell = cell;
        this.date = LocalDateTime.now();
    }
}
