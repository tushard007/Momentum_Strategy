package com.stock.analysis.Momentum_Strategy.controller;

import com.opencsv.exceptions.CsvException;
import com.stock.analysis.Momentum_Strategy.model.Stock;
import com.stock.analysis.Momentum_Strategy.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/")
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public Optional<Stock> getStockById(@PathVariable int id) {
        return stockService.getStockById(id);
    }

    @PostMapping("/")
    public Stock createStock(@RequestBody Stock stock) {
        return stockService.saveOrUpdateStock(stock);
    }
    @PostMapping("/CSVUpload")
    public void StockCsvUpload() throws IOException, CsvException {
      int dataUpdated=  stockService.CSVStockUpload();
        System.out.println(dataUpdated);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable int id) {
        stockService.deleteStock(id);
    }
}
