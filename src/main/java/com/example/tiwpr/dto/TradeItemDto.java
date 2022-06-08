package com.example.tiwpr.dto;

import com.example.tiwpr.entity.Account;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TradeItemDto {

    private Long id;

    private BigDecimal price;

    private Account previousOwner;

    private Account newOwner;

    private LocalDateTime tradeTime;
}
