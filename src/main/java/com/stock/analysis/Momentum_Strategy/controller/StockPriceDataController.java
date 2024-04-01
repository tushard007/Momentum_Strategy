package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.model.StockPriceData;
import com.stock.analysis.Momentum_Strategy.services.StockPriceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stockpricedata")
public class StockPriceDataController {

    @Autowired
    private StockPriceDataService service;

    @GetMapping
    public List<StockPriceData> getAllStockPriceData() {
        return service.getAllStockPriceData();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockPriceData> getStockPriceDataById(@PathVariable int id) {
        Optional<StockPriceData> stockPriceData = service.getStockPriceDataById(id);
        return stockPriceData.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<StockPriceData> createStockPriceData(@RequestBody StockPriceData stockPriceData) {
        StockPriceData createdStockPriceData = service.saveOrUpdateStockPriceData(stockPriceData);
        return ResponseEntity.ok().body(createdStockPriceData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockPriceData> updateStockPriceData(@PathVariable int id, @RequestBody StockPriceData stockPriceData) {
        if (!service.getStockPriceDataById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        StockPriceData updatedStockPriceData = service.saveOrUpdateStockPriceData(stockPriceData);
        return ResponseEntity.ok().body(updatedStockPriceData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockPriceData(@PathVariable int id) {
        if (!service.getStockPriceDataById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteStockPriceData(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/StockReturn1Year")
    public void getAllStockPriceReturnForYear() {
         service.getAllStockPriceReturnForYear();
    }
}
