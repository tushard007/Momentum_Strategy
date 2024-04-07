package com.stock.analysis.Momentum_Strategy.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stock.analysis.Momentum_Strategy.model.StockReturn;

@Repository
public interface StockReturnRepository extends JpaRepository<StockReturn, Integer> {
}
