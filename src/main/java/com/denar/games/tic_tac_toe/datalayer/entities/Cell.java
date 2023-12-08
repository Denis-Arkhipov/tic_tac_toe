package com.denar.games.tic_tac_toe.datalayer.entities;

import com.denar.games.tic_tac_toe.datalayer.enums.CellValues;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer index;

    @Enumerated(EnumType.STRING)
    private CellValues cellValue;

    public Cell(Integer index, CellValues cellValue) {
        this.index = index;
        this.cellValue = cellValue;
    }
}
