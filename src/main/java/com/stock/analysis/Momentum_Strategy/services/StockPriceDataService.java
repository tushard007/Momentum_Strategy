package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.Util.ReturnCalculationUtils;
import com.stock.analysis.Momentum_Strategy.dao.StockPriceDataRepository;
import com.stock.analysis.Momentum_Strategy.model.StockMomentum;
import com.stock.analysis.Momentum_Strategy.model.StockPriceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class StockPriceDataService {

    @Autowired
    private StockPriceDataRepository repository;

    public List<StockPriceData> getAllStockPriceData() {
        return repository.findAll();
    }


    public Optional<StockPriceData> getStockPriceDataById(int id) {
        return repository.findById(id);
    }

    public StockPriceData saveOrUpdateStockPriceData(StockPriceData stockPriceData) {
        return repository.save(stockPriceData);
    }

    public void deleteStockPriceData(int id) {
        repository.deleteById(id);
    }
}
