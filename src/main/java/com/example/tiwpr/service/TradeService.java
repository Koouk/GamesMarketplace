package com.example.tiwpr.service;

import com.example.tiwpr.dto.TradeRequest;
import com.example.tiwpr.dto.TransferRequestDto;
import com.example.tiwpr.entity.Account;
import com.example.tiwpr.entity.Game;
import com.example.tiwpr.entity.SaleItem;
import com.example.tiwpr.entity.TradeItem;
import com.example.tiwpr.exception.BadRequestException;
import com.example.tiwpr.exception.ResourceNotFoundException;
import com.example.tiwpr.repository.AccountRepository;
import com.example.tiwpr.repository.GameRepository;
import com.example.tiwpr.repository.SaleItemRepository;
import com.example.tiwpr.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    private final SaleItemRepository saleItemRepository;

    private final AccountRepository accountRepository;

    private final GameRepository gameRepository;

    public Page<TradeItem> getAllTrades(Pageable pageable) {
        return tradeRepository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TradeItem buyGame(TradeRequest tradeRequest) {
        SaleItem saleItem =  saleItemRepository
                .findById(tradeRequest.saleItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find sale item " + tradeRequest.saleItemId()));

        Game gameToSell = saleItem.getGame();
        Account previousOwner = gameToSell.getOwner();
        Account newOwner =  accountRepository.findById(tradeRequest.buyerId()).orElseThrow(() -> new ResourceNotFoundException("Could not find user " + tradeRequest.buyerId()));

        if(newOwner.getAccountBalance().compareTo(saleItem.getPrice()) < 0 ) {
            throw new BadRequestException("Not enough money to buy this game");
        }

        TradeItem tradeItem = TradeItem.builder()
                .price(saleItem.getPrice())
                .game(gameToSell)
                .previousOwner(previousOwner)
                .newOwner(newOwner)
                .build();

        newOwner.setAccountBalance(newOwner.getAccountBalance().subtract(saleItem.getPrice()));
        newOwner.getBoughtItems().add(tradeItem);
        newOwner.getGames().add(gameToSell);

        previousOwner.setAccountBalance(previousOwner.getAccountBalance().add(saleItem.getPrice()));
        previousOwner.getSoldItems().add(tradeItem);
        previousOwner.getGames().remove(gameToSell);

        gameToSell.setSaleItem(null);
        gameToSell.setOwner(newOwner);

        tradeRepository.save(tradeItem);
        accountRepository.save(newOwner);
        accountRepository.save(previousOwner);
        saleItemRepository.delete(saleItem);
        return  tradeItem;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transferGames(TransferRequestDto transferRequest) {
        List<Game> gamesToTransfer = Arrays.stream(transferRequest.getGamesId())
                .map(game -> gameRepository.findById(game).orElseThrow(() -> new ResourceNotFoundException("Could not find user " + game)))
                .toList();
        Account currentOwner  =  accountRepository.findById(transferRequest.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Could not find user " + transferRequest.getOwnerId()));
        Account newOwner  =  accountRepository.findById(transferRequest.getReceiverId()).orElseThrow(() -> new ResourceNotFoundException("Could not find user " + transferRequest.getReceiverId()));

        gamesToTransfer.forEach(game -> transferGame(game, currentOwner, newOwner));

        gameRepository.saveAll(gamesToTransfer);
        accountRepository.save(currentOwner);
        accountRepository.save(newOwner);

    }

    private void transferGame(Game game, Account currentOwner, Account newOwner) {
        if(!game.getOwner().getId().equals(currentOwner.getId())) {
            throw new BadRequestException("Wrong owner id");
        }

        currentOwner.getGames().remove(game);
        newOwner.getGames().add(game);
        game.setOwner(newOwner);
    }

}
