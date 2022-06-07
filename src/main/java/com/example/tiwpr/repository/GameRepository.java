package com.example.tiwpr.repository;

import com.example.tiwpr.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {


    Page<Game> findAllByOwner_IdOrderByIdAsc(long ownerId, Pageable pageable);

    Optional<Game> findGameByIdAndOwner_Id(long id, long ownerId);

}