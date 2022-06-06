package com.example.tiwpr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name="game")
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Game_ID_GEN")
    @SequenceGenerator(name = "Game_ID_GEN", sequenceName = "Game_ID_SEQ", allocationSize = 1)
    private Long id;

    private Date buyDate;

    @ManyToOne
    private Account owner;

}
