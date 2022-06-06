package com.example.tiwpr.repository;

import com.example.tiwpr.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}