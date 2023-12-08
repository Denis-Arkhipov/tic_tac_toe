package com.denar.games.tic_tac_toe.datalayer.repository;

import com.denar.games.tic_tac_toe.datalayer.entities.Board;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BoardRepository extends CrudRepository<Board, Long> {
    Board findByKey(UUID key);
}
