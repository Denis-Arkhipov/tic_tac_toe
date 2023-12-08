package com.denar.games.tic_tac_toe.controller;

import com.denar.games.tic_tac_toe.dto.GameDto;
import com.denar.games.tic_tac_toe.services.GameProcessor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class GameController {
    private static final String COOKIE_BOARD_KEY = "boardKey";
    private final GameProcessor processor;

    /** initiator 0 = пользователь, 1 = машина */
    @GetMapping("/new-game")
    public ResponseEntity<GameDto> newGame(@RequestParam("initiator") Integer initiator,
                                           HttpServletResponse response) {
        GameDto gameDto = processor.newGame(initiator);
        response.addCookie(createBoardCookie(gameDto.getGameId().toString()));

        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }

    @PostMapping("/step")
    public ResponseEntity<GameDto> makeStep(@RequestParam Integer cellIndex, HttpServletRequest request) {
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(COOKIE_BOARD_KEY))
                .findFirst()
                .orElse(null);
        GameDto gameDto = processor.processUserMove(cookie.getValue(), cellIndex);

        return new ResponseEntity<>(gameDto, HttpStatus.OK);
    }

    private Cookie createBoardCookie(String boardKey) {
        Cookie cookie = new Cookie("boardKey", boardKey);
        cookie.setPath("/");

        return cookie;
    }
}
