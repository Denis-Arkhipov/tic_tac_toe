package com.denar.games.tic_tac_toe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BoardDto {
    private String[] cells = new String[9];
}
