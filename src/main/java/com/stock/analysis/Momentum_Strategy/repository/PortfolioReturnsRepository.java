package com.stock.analysis.Momentum_Strategy.repository;

import com.stock.analysis.Momentum_Strategy.model.PortfolioReturns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioReturnsRepository extends JpaRepository<PortfolioReturns,Integer> {
}
