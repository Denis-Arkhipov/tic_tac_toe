package com.denar.games.tic_tac_toe.controller;

import com.denar.games.tic_tac_toe.dto.GameDto;
import com.denar.games.tic_tac_toe.services.GameProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class GameController {
    private final GameProcessor processor;

    /** initiator 1 = пользователь, 0 = машина */
    @GetMapping("/new-game")
    public ResponseEntity<GameDto> newGame(@RequestParam("initiator") int initiator) {
        return new ResponseEntity<>(processor.newGame(initiator), HttpStatus.CREATED);
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<GameDto> getCurrentBoard(@PathVariable("id") String boardId) {
        return new ResponseEntity<>(processor.getBoard(boardId), HttpStatus.OK);
    }

    @PostMapping("/moves")
    public ResponseEntity<GameDto> makeMove(@RequestParam int cellIndex, @RequestParam("boardId") String boardId) {
        GameDto gameDto = processor.processUserMove(boardId, cellIndex);
        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }

    @DeleteMapping("/moves/{id}")
    public ResponseEntity<GameDto> cancelMove(@PathVariable("id") String boardId) {
        return new ResponseEntity<>(processor.cancelLastMove(boardId), HttpStatus.OK);
    }

    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<String> handleGameException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleMoveCancelException(IllegalStateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
