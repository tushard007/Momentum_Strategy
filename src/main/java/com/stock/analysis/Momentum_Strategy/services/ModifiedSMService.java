package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.dao.ModifiedSMRepository;
import com.stock.analysis.Momentum_Strategy.model.ModifiedStockMomentum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ModifiedSMService {

    @Autowired
    private ModifiedSMRepository repository;

    public List<ModifiedStockMomentum> getAllStocks() {
        return repository.findAll();
    }

    public Optional<ModifiedStockMomentum> getStockById(int id) {
        return repository.findById(id);
    }

    public ModifiedStockMomentum saveOrUpdateStock(ModifiedStockMomentum stock) {
        return repository.save(stock);
    }

    public void deleteStock(int id) {
        repository.deleteById(id);
    }
}
