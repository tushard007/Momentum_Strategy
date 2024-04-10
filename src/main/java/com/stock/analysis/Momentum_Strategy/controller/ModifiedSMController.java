package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.model.ModifiedStockMomentum;
import com.stock.analysis.Momentum_Strategy.services.ModifiedSMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modifiedSM")
public class ModifiedSMController {

    @Autowired
    private ModifiedSMService modifiedSMService;

    @GetMapping
    public List<ModifiedStockMomentum> getAllStocks() {
        return modifiedSMService.getAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModifiedStockMomentum> getStockById(@PathVariable int id) {
        Optional<ModifiedStockMomentum> stock = modifiedSMService.getStockById(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ModifiedStockMomentum> createStock(@RequestBody ModifiedStockMomentum stock) {
        ModifiedStockMomentum createdStock = modifiedSMService.saveOrUpdateStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModifiedStockMomentum> updateStock(@PathVariable int id, @RequestBody ModifiedStockMomentum stock) {
        if (!modifiedSMService.getStockById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ModifiedStockMomentum updatedStock = modifiedSMService.saveOrUpdateStock(stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable int id) {
        if (!modifiedSMService.getStockById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        modifiedSMService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{endDate}")
    public ResponseEntity<Void> getTotalRanking(@PathVariable Date endDate){
        modifiedSMService.getSMTotalRanking(endDate);
        return ResponseEntity.noContent().build();
    }

}
