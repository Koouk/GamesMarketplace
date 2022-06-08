package com.example.tiwpr.controller;

import com.example.tiwpr.dto.TradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@RestController
@RequiredArgsConstructor
public class UtilController {

    @GetMapping("/trade")
    public Page<TradeDto> getAllTrades(Pageable pageable) {
        return null;

    }

    @PostMapping("/trade")
    public TradeDto tradeItem() {
        return null;
    }

    @PostMapping("/transfer")
    public void transferManyGames() {

    }

}
