package com.stock.analysis.Momentum_Strategy.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.stock.analysis.Momentum_Strategy.model.StockReturn;

import java.sql.Date;
import java.util.List;

@Repository
public interface StockReturnRepository extends JpaRepository<StockReturn, Integer> {
    @Query(value = "select * from stock_return where end_date = :stockDate", nativeQuery = true)
    List<StockReturn> findByByEndDate(@Param("stockDate") Date endStockDate);

    @Query(value = "select * from stock_return where end_date = :stockDate and month_time_period=:timePeriod", nativeQuery = true)
    List<StockReturn> findByByEndDateAndMonthTimePeriod(@Param("stockDate") Date endStockDate,@Param("timePeriod") int timePeriod);
}
