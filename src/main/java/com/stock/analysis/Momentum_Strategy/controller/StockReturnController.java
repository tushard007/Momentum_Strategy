package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.model.StockReturn;
import com.stock.analysis.Momentum_Strategy.services.StockReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/stockReturn")
@Slf4j
public class StockReturnController {

    @Autowired
    private StockReturnService stockReturnService;

    @GetMapping("/{id}")
    public ResponseEntity<StockReturn> getStockById(@PathVariable int id) {
        StockReturn stock = stockReturnService.getStockById(id);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StockReturn> createStock(@RequestBody StockReturn stockReturn) {
        StockReturn createdStock = stockReturnService.saveStockReturn(stockReturn);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockReturn> updateStock(@PathVariable int id, @RequestBody StockReturn stockReturn) {
        StockReturn updatedStock = stockReturnService.updateStockReturn(id, stockReturn);
        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable int id) {
        stockReturnService.deleteStockReturn(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{endDate}/{month}")
    public ResponseEntity<List<StockReturn>> getStockReturn(@PathVariable Date endDate, @PathVariable int month){
    List<StockReturn> returnWiseList=stockReturnService.getMonthlyStockReturn(endDate,month);
        return new ResponseEntity<>(returnWiseList, HttpStatus.OK);
    }
}

