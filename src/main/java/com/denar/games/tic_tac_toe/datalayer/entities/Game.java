package com.denar.games.tic_tac_toe.datalayer.entities;

import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue
    private UUID id;
    private GameStatus status;

    @JoinColumn(name = "board_id")
    private BoardEntity board = new BoardEntity();
}
