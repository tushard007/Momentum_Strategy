package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.dao.PureSMRepository;
import com.stock.analysis.Momentum_Strategy.model.PureStockMomentum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PureSMService {
    @Autowired
    private PureSMRepository pureSMRepository;

    public List<PureStockMomentum> getAllStocks() {
        return pureSMRepository.findAll();
    }

    public Optional<PureStockMomentum> getStockById(int id) {
        return pureSMRepository.findById(id);
    }

    public PureStockMomentum saveOrUpdateStock(PureStockMomentum stock) {
        return pureSMRepository.save(stock);
    }

    public void deleteStock(int id) {
        pureSMRepository.deleteById(id);
    }
}
