package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.model.ModifiedStockMomentum;
import com.stock.analysis.Momentum_Strategy.services.ModifiedSMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modifiedSM")
public class ModifiedSMController {

    @Autowired
    private ModifiedSMService service;

    @GetMapping
    public List<ModifiedStockMomentum> getAllStocks() {
        return service.getAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModifiedStockMomentum> getStockById(@PathVariable int id) {
        Optional<ModifiedStockMomentum> stock = service.getStockById(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ModifiedStockMomentum> createStock(@RequestBody ModifiedStockMomentum stock) {
        ModifiedStockMomentum createdStock = service.saveOrUpdateStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModifiedStockMomentum> updateStock(@PathVariable int id, @RequestBody ModifiedStockMomentum stock) {
        if (!service.getStockById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ModifiedStockMomentum updatedStock = service.saveOrUpdateStock(stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable int id) {
        if (!service.getStockById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
