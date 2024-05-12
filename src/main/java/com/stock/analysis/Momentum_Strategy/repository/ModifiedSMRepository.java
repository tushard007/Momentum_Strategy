package com.stock.analysis.Momentum_Strategy.repository;

import com.stock.analysis.Momentum_Strategy.model.ModifiedStockMomentum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ModifiedSMRepository extends JpaRepository<ModifiedStockMomentum, Integer> {

    @Query(value = "select stock_name from modified_stock_momentum where end_date = :stockDate", nativeQuery = true)
    Set<String> findStockNameByEndDate(@Param("stockDate") Date endStockDate);

    @Query(value = "select distinct(end_date) from modified_stock_momentum order by end_date", nativeQuery = true)
    List<Date> findDistinctByEndDate();
}





