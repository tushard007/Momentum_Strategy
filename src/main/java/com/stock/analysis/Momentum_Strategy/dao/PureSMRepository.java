package com.stock.analysis.Momentum_Strategy.dao;

import com.stock.analysis.Momentum_Strategy.model.PureStockMomentum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PureSMRepository extends JpaRepository<PureStockMomentum, Integer> {
    // You can add custom query methods here if needed
}
