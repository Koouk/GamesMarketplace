package com.example.tiwpr.service;

import com.example.tiwpr.dto.GameDto;
import com.example.tiwpr.entity.Account;
import com.example.tiwpr.entity.Game;
import com.example.tiwpr.exception.BadRequestException;
import com.example.tiwpr.exception.ResourceNotFoundException;
import com.example.tiwpr.exception.WrongVersionException;
import com.example.tiwpr.mapper.AccountGamesMapper;
import com.example.tiwpr.repository.AccountRepository;
import com.example.tiwpr.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountGamesService {

    private final GameRepository gameRepository;

    private final AccountRepository accountRepository;

    private final AccountGamesMapper accountGamesMapper;

    public Page<Game> getAllUserGamesPageable(Long ownerId, Pageable pageable) {
        return gameRepository.findAllByOwner_IdOrderByIdAsc(ownerId, pageable);

    }

    public Game addNewGame(GameDto gameDto, Long userId) {
        Game newGame = accountGamesMapper.mapGameDtoToGame(gameDto);
        Account owner = accountRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Could not find user " + userId));
        newGame.setOwner(owner);
        gameRepository.save(newGame);
        return newGame;
    }

    public Optional<Game> findGame(Long gameId) {
        return gameRepository.findById(gameId);
    }

    public void updateGame(Long gameId, GameDto game, String etag) {
        if(!game.getId().equals(gameId)) {
            throw new BadRequestException("Wrong game id");
        }

        gameRepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException("Could not find game " + gameId));
        Game updatedGame = accountGamesMapper.mapGameDtoToGame(game);
        Account owner = accountRepository.findById(game.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Could not find account " + game.getOwnerId()));
        updatedGame.setId(gameId);
        updatedGame.setOwner(owner);
        updatedGame.setVersion(Long.valueOf(etag));

        gameRepository.save(updatedGame);
    }

    public void partialUpdateGame(Long gameId, GameDto game, String etag) {
        Game currentGame = gameRepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException("Could not find game " + gameId));
        if(!currentGame.getVersion().equals(Long.valueOf(etag))) { //todo hibernate session messing up, fix it later
            throw new WrongVersionException("Wrong version");
        }

        Game updatedGame = accountGamesMapper.mergeGameDtoWithGame(currentGame, game);

        if(game.getOwnerId() != null) {
            Account owner = accountRepository.findById(game.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Could not find account " + game.getOwnerId()));
            updatedGame.setOwner(owner);
        }

        gameRepository.save(updatedGame);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteGame(Long gameId) {
        Game currentGame = gameRepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException("Could not find game " + gameId));
        Account owner = currentGame.getOwner();
        owner.getGames().remove(currentGame);
        gameRepository.delete(currentGame);
        accountRepository.save(owner);
    }
}
