package com.denar.games.tic_tac_toe.datalayer.repository;

import com.denar.games.tic_tac_toe.datalayer.entities.Cell;
import org.springframework.data.repository.CrudRepository;

public interface CellRepository extends CrudRepository<Cell, Long> {
}
