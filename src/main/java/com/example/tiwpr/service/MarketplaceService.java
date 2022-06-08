package com.example.tiwpr.service;

import com.example.tiwpr.dto.SaleItemDto;
import com.example.tiwpr.entity.Account;
import com.example.tiwpr.entity.Game;
import com.example.tiwpr.entity.SaleItem;
import com.example.tiwpr.exception.BadRequestException;
import com.example.tiwpr.exception.ResourceNotFoundException;
import com.example.tiwpr.exception.WrongVersionException;
import com.example.tiwpr.mapper.SaleItemMapper;
import com.example.tiwpr.repository.GameRepository;
import com.example.tiwpr.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketplaceService {

    private final SaleItemRepository saleItemRepository;

    private final SaleItemMapper saleItemMapper;

    private final GameRepository gameRepository;

    public Page<SaleItem> getAllItemsToSell(Pageable pageable) {
        return saleItemRepository.findAll(pageable);
    }

    public SaleItem createNewSaleItem(SaleItemDto saleItem) {
        SaleItem newSaleItem = saleItemMapper.mapSaleItemDtoToSaleItem(saleItem);
        Game game = gameRepository.findById(saleItem.getGameId()).orElseThrow(() -> new ResourceNotFoundException("Could not find game " + saleItem.getGameId()));
        newSaleItem.setGame(game);
        saleItemRepository.save(newSaleItem);
        return newSaleItem;
    }
    public Optional<SaleItem> findItem(Long gameId) {
        return saleItemRepository.findById(gameId);
    }


    public void updateSaleItem(Long itemId, SaleItemDto saleItemDto, String etag) {
        if(!saleItemDto.getId().equals(itemId)) {
            throw new BadRequestException("Wrong item id");
        }

        saleItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Could not find sale item " + itemId));
        SaleItem updatedSaleItem = saleItemMapper.mapSaleItemDtoToSaleItem(saleItemDto);
        Game game = gameRepository.findById(saleItemDto.getGameId()).orElseThrow(() -> new ResourceNotFoundException("Could not find game " + saleItemDto.getGameId()));
        updatedSaleItem.setId(itemId);
        updatedSaleItem.setGame(game);
        updatedSaleItem.setVersion(Long.valueOf(etag));

        saleItemRepository.save(updatedSaleItem);
    }

    public void partialUpdateSaleItem(Long itemId, SaleItemDto saleItemDto, String etag) {
        SaleItem saleItemToChange = saleItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Could not find saleItem " + itemId));
        if(!saleItemToChange.getVersion().equals(Long.valueOf(etag))) { //todo hibernate session messing up, fix it later
            throw new WrongVersionException("Wrong version");
        }

        SaleItem updatedSaleItem = saleItemMapper.mergeSaleItemDtoWithSaleItem(saleItemToChange, saleItemDto);
        if(saleItemDto.getGameId() != null) {
            Game game = gameRepository.findById(saleItemDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Could not find account " + saleItemDto.getGameId()));
            updatedSaleItem.setGame(game);
        }
        saleItemRepository.save(updatedSaleItem);
    }

    public void deleteSaleItem(Long itemId) {
        SaleItem currentItem = saleItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Could not find game " + itemId));
        Game game = currentItem.getGame();
        game.setSaleItem(null);
        saleItemRepository.delete(currentItem);
        gameRepository.save(game);
    }


}
