package com.denar.games.tic_tac_toe.datalayer.entities;

import com.denar.games.tic_tac_toe.datalayer.enums.GameStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID key = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.GAME;

    @OneToMany
    @JoinTable(
            name="BoardCells",
            joinColumns={@JoinColumn(name="board_id", referencedColumnName="id")})
    @MapKey(name = "index")
    private Map<Integer, Cell> cells;
}
