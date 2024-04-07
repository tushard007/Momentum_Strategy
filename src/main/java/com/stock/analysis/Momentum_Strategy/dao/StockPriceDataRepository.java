package com.stock.analysis.Momentum_Strategy.dao;

import com.stock.analysis.Momentum_Strategy.model.StockPriceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface StockPriceDataRepository extends JpaRepository<StockPriceData, Integer> {


    @Query(value = "select * from stock_price_data where price_date in :SEDate", nativeQuery = true)
    List<StockPriceData> findByPriceDate(@Param("SEDate") List<Date> startEndDate);
     int countByPriceDate(@Param("endStockDate")Date endDate);
}
