package com.example.tiwpr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "trade_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TradeItem_ID_GEN")
    @SequenceGenerator(name = "TradeItem_ID_GEN", sequenceName = "TradeItem_ID_SEQ", allocationSize = 1)
    private Long id;

    private BigDecimal price;

    @ManyToOne
    private Game game;

    @ManyToOne
    private Account previousOwner;

    @ManyToOne
    private Account newOwner;

    private LocalDateTime tradeTime;

    @PrePersist
    private void preSetTimestamps() {
        tradeTime = LocalDateTime.now();
    }

    public LocalDateTime getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(LocalDateTime tradeTime) {
        this.tradeTime = tradeTime;
    }

    public LocalDateTime getCreateTimestamp() {
        return tradeTime;
    }

    public void setCreateTimestamp(LocalDateTime createTimestamp) {
        this.tradeTime = createTimestamp;
    }

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Account getPreviousOwner() {
        return previousOwner;
    }

    public void setPreviousOwner(Account previousOwner) {
        this.previousOwner = previousOwner;
    }

    public Account getNewOwner() {
        return newOwner;
    }

    public void setNewOwner(Account newOwner) {
        this.newOwner = newOwner;
    }
}
