package com.denar.games.tic_tac_toe.datalayer.repository;

import com.denar.games.tic_tac_toe.datalayer.entities.Move;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MoveRepository extends CrudRepository<Move, Long> {
    List<Move> findTop2ByBoardIdOrderByDateDesc(UUID boardId);
}
