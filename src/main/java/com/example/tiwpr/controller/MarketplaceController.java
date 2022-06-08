package com.example.tiwpr.controller;

import com.example.tiwpr.dto.SaleItemDto;
import com.example.tiwpr.entity.SaleItem;
import com.example.tiwpr.mapper.SaleItemMapper;
import com.example.tiwpr.service.MarketplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/marketplace")
@RequiredArgsConstructor
public class MarketplaceController {

    private final MarketplaceService marketplaceService;

    private final SaleItemMapper saleItemMapper;

    @GetMapping
    public Page<SaleItemDto> getAllItemsToSell(Pageable pageable) {
        return marketplaceService.getAllItemsToSell(pageable)
                .map(saleItemMapper::mapSaleItemToSaleItemDto);
    }

    @PostMapping
    public ResponseEntity<SaleItemDto> createNewSaleItem(@RequestBody @Valid SaleItemDto saleItem) {
        SaleItem newSaleItem = marketplaceService.createNewSaleItem(saleItem);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .eTag(String.valueOf(newSaleItem.getVersion()))
                .body(saleItemMapper.mapSaleItemToSaleItemDto(newSaleItem));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<SaleItemDto> getUserGame(@PathVariable Long itemId) {
        Optional<SaleItem> saleItem = marketplaceService.findItem(itemId);
        return saleItem
                .map(si -> ResponseEntity
                        .status(HttpStatus.OK)
                        .eTag(String.valueOf(si.getVersion()))
                        .body(saleItemMapper.mapSaleItemToSaleItemDto(si)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateSaleItem(@PathVariable Long itemId, @RequestBody @Valid SaleItemDto saleItemDto, @RequestHeader(name = "If-Match") String etag) {
        marketplaceService.updateSaleItem(itemId, saleItemDto, etag);

    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public void partialUpdateSaleItem(@PathVariable Long itemId, @RequestBody SaleItemDto saleItemDto, @RequestHeader(name = "If-Match") String etag) {
        marketplaceService.partialUpdateSaleItem(itemId, saleItemDto, etag);

    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSaleItem(@PathVariable Long itemId) {
        marketplaceService.deleteSaleItem(itemId);
    }


}
