package com.example.tiwpr.repository;

import com.example.tiwpr.entity.TradeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeItem, Long> {

}
