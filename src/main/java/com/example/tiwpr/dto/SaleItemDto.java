package com.example.tiwpr.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class SaleItemDto {

    private Long id;

    @NotNull
    private BigDecimal price;

    private String description;

    private Long gameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long game_id) {
        this.gameId = game_id;
    }
}
