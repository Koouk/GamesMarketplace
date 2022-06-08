package com.example.tiwpr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Game_ID_GEN")
    @SequenceGenerator(name = "Game_ID_GEN", sequenceName = "Game_ID_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    private String title;

    private Date buyDate;

    private String description;

    @ManyToOne
    private Account owner;

    @OneToOne(mappedBy = "game", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private SaleItem saleItem;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<TradeItem> tradeHistory;


    public SaleItem getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(SaleItem saleItem) {
        this.saleItem = saleItem;
    }

    @Version
    private Long version;

    public List<TradeItem> getTradeHistory() {
        return tradeHistory;
    }

    public void setTradeHistory(List<TradeItem> tradeHistory) {
        this.tradeHistory = tradeHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
