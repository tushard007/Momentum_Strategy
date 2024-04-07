package com.stock.analysis.Momentum_Strategy.dao;
import com.stock.analysis.Momentum_Strategy.model.ModifiedStockMomentum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ModifiedSMRepository extends JpaRepository<ModifiedStockMomentum, Integer> {
}





