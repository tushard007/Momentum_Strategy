package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.repository.StockPriceDataRepository;
import com.stock.analysis.Momentum_Strategy.model.StockPriceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
