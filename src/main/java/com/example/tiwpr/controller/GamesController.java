package com.example.tiwpr.controller;

import com.example.tiwpr.dto.GameDto;
import com.example.tiwpr.entity.Game;
import com.example.tiwpr.mapper.AccountGamesMapper;
import com.example.tiwpr.service.AccountGamesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GamesController {

    private final AccountGamesService accountGamesService;

    private final AccountGamesMapper accountGamesMapper;

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getUserGame(@PathVariable Long gameId) {
        Optional<Game> game = accountGamesService.findGame(gameId);
        return game
                .map(g -> ResponseEntity
                        .status(HttpStatus.OK)
                        .eTag(String.valueOf(g.getVersion()))
                        .body(accountGamesMapper.mapGameToGameDto(g)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable Long gameId, @RequestBody @Valid GameDto game, @RequestHeader(name = "If-Match") String etag) {
        accountGamesService.updateGame(gameId, game, etag);

    }

    @PatchMapping("/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public void partialUpdateUser(@PathVariable Long gameId, @RequestBody GameDto game, @RequestHeader(name = "If-Match") String etag) {
        accountGamesService.partialUpdateGame(gameId, game, etag);

    }

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long gameId) {
        accountGamesService.deleteGame(gameId);
    }



}
