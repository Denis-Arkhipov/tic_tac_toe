package com.denar.games.tic_tac_toe.datalayer.repository;

import com.denar.games.tic_tac_toe.datalayer.entities.BoardEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<BoardEntity, Long> {
//    BoardEntity findByKey(String key);
}
