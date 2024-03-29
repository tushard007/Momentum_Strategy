package com.stock.analysis.Momentum_Strategy.dao;

import com.stock.analysis.Momentum_Strategy.model.Etf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtfRepository extends JpaRepository<Etf, Integer> {
}
