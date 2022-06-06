package com.example.tiwpr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_GEN")
    @SequenceGenerator(name = "ACCOUNT_GEN", sequenceName = "ACCOUNT_ID_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String nick;

    @Column(unique = true)
    private String email;

    private BigDecimal accountBalance;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Game> games = new ArrayList<>();

    @Version
    Long version;

}
