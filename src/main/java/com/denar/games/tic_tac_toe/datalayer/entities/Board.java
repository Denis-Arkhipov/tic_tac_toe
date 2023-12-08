package com.denar.games.tic_tac_toe.datalayer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//@Entity
//@Getter
//@Setter
public class Board {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cell0;
    private String cell1;
    private String cell2;
    private String cell3;
    private String cell4;
    private String cell5;
    private String cell6;
    private String cell7;
    private String cell8;

//    @OneToOne
    private Game game;
}
