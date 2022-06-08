package com.example.tiwpr.controller;

import com.example.tiwpr.dto.AccountDto;
import com.example.tiwpr.dto.GameDto;
import com.example.tiwpr.dto.PatchOperation;
import com.example.tiwpr.entity.Account;
import com.example.tiwpr.entity.Game;
import com.example.tiwpr.mapper.AccountGamesMapper;
import com.example.tiwpr.mapper.AccountMapper;
import com.example.tiwpr.service.AccountGamesService;
import com.example.tiwpr.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.Optional;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    private final AccountGamesService accountGamesService;

    private final AccountGamesMapper accountGamesMapper;

    @GetMapping
    public Page<AccountDto> getAllUsers(Pageable pageable) {
        return accountService.getAllUsersPageable(pageable)
                .map(accountMapper::mapAccountToAccountDto);
    }

    @PostMapping
    public ResponseEntity<AccountDto> createNewUser(@RequestBody @Validated(Default.class) AccountDto user) {
        Account account = accountService.createNewUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .eTag(String.valueOf(account.getVersion()))
                .body(accountMapper.mapAccountToAccountDto(account));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AccountDto> getUser(@PathVariable Long userId) {
        Optional<Account> account =  accountService.find(userId);
        return account
                .map(acc -> ResponseEntity
                        .status(HttpStatus.OK)
                        .eTag(String.valueOf(acc.getVersion()))
                        .body(accountMapper.mapAccountToAccountDto(acc)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable Long userId, @RequestBody @Validated(Default.class) AccountDto user, @RequestHeader(name = "If-Match") String etag) {
        accountService.updateUser(userId, user, etag);

    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void partialUpdateUser(@PathVariable Long userId, @RequestBody @Validated(PatchOperation.class) AccountDto user, @RequestHeader("If-Match") String etag) {
        accountService.partialUpdateUser(userId, user, etag);

    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long userId) {
        accountService.deleteUser(userId);
    }


    @GetMapping("/{userId}/games")
    public Page<GameDto> getAllUserGames(@PathVariable Long userId, Pageable pageable) {
        return accountGamesService.getAllUserGamesPageable(userId, pageable)
                .map(accountGamesMapper::mapGameToGameDto);
    }

    @PostMapping("/{userId}/games")
    public ResponseEntity<GameDto> addUserGame(@RequestBody @Valid GameDto gameDto,@PathVariable Long userId) {
        Game game = accountGamesService.addNewGame(gameDto, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .eTag(String.valueOf(game.getVersion()))
                .body(accountGamesMapper.mapGameToGameDto(game));
    }
}
