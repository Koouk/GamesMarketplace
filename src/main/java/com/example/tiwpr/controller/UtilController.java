package com.example.tiwpr.controller;

import com.example.tiwpr.dto.TradeItemDto;
import com.example.tiwpr.dto.TradeRequest;
import com.example.tiwpr.dto.TransferRequestDto;
import com.example.tiwpr.mapper.TradeItemMapper;
import com.example.tiwpr.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UtilController {

    private final TradeService tradeService;

    private final TradeItemMapper tradeItemMapper;


    @GetMapping("/trade")
    @ResponseStatus(HttpStatus.OK)
    public Page<TradeItemDto> getAllTrades(Pageable pageable) {
        return tradeService.getAllTrades(pageable)
                .map(tradeItemMapper::mapTradeItemToTradeItemDto);

    }

    @PostMapping("/trade")
    @ResponseStatus(HttpStatus.CREATED)
    public TradeItemDto tradeItem(@RequestBody TradeRequest tradeRequest) {
        return tradeItemMapper.mapTradeItemToTradeItemDto(
                tradeService.buyGame(tradeRequest));
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public void transferManyGames(@RequestBody TransferRequestDto transferRequestDto) {
        tradeService.transferGames(transferRequestDto);
    }

}
