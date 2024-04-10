package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.model.PureStockMomentum;
import com.stock.analysis.Momentum_Strategy.services.PureSMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pureSM")
public class PureSMController {
    @Autowired
    private PureSMService pureSMService;

    @GetMapping
    public List<PureStockMomentum> getAllStocks() {
        return pureSMService.getAllStocks();
    }

    @GetMapping("/{id}")
    public PureStockMomentum getStockById(@PathVariable int id) {
        Optional<PureStockMomentum> stock = pureSMService.getStockById(id);
        return stock.orElse(null); // Return null if stock is not found
    }

    @PostMapping
    public PureStockMomentum addStock(@RequestBody PureStockMomentum stock) {
        return pureSMService.saveOrUpdateStock(stock);
    }

    @PutMapping("/{id}")
    public PureStockMomentum updateStock(@PathVariable int id, @RequestBody PureStockMomentum updatedStock) {
        updatedStock.setId(id);
        return pureSMService.saveOrUpdateStock(updatedStock);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable int id) {
        pureSMService.deleteStock(id);
    }

    @PostMapping("/{endDate}/{timePeriod}")
    public ResponseEntity<Void> getTopPureMomentumStocks(@PathVariable Date endDate,@PathVariable int timePeriod) {
        pureSMService.getTopPureMomentumStocks(endDate,timePeriod);
        return ResponseEntity.noContent().build();
    }
}
