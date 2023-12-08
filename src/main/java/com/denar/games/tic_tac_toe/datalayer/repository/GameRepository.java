package com.denar.games.tic_tac_toe.datalayer.repository;

import com.denar.games.tic_tac_toe.datalayer.entities.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GameRepository extends CrudRepository<Game, UUID> {
}
