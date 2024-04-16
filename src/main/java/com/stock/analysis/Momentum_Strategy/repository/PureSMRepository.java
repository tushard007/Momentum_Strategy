package com.stock.analysis.Momentum_Strategy.repository;

import com.stock.analysis.Momentum_Strategy.model.PureStockMomentum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Set;

@Repository
public interface PureSMRepository extends JpaRepository<PureStockMomentum, Integer> {
    @Query(value = "select stock_name from pure_stock_momentum where end_date = :stockDate", nativeQuery = true)
    Set<String> findStockNameByEndDate(@Param("stockDate") Date endStockDate);
}
