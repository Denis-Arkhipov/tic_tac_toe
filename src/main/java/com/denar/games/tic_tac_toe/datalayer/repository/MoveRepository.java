package com.denar.games.tic_tac_toe.datalayer.repository;

import com.denar.games.tic_tac_toe.datalayer.entities.Move;
import org.springframework.data.repository.CrudRepository;

public interface MoveRepository extends CrudRepository<Move, Long> {
}
