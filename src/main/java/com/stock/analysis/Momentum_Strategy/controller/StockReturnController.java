package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.model.StockMomentum;
import com.stock.analysis.Momentum_Strategy.model.StockReturn;
import com.stock.analysis.Momentum_Strategy.services.StockReturnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stockReturn")
@Slf4j
public class StockReturnController {

    @Autowired
    private StockReturnService stockReturnService;


    @GetMapping
    public ResponseEntity<List<StockReturn>> getAllStocks() {
        List<StockReturn> stocks = (List<StockReturn>) stockReturnService.getAllStocks();

        Map<String, List<StockReturn>> StockreturnByNameMap = stocks.stream()
                .collect(Collectors.groupingBy(StockReturn::getStockName));
        List<StockMomentum> smList=new ArrayList<>();
        for (Map.Entry<String, List<StockReturn>> entry : StockreturnByNameMap.entrySet()) {
            StockMomentum sm=new StockMomentum();
            sm.setStockName(entry.getKey());
           int count=0;
           int calculateTotalRank=0;
            for (StockReturn sr : entry.getValue()) {
                count++;
                calculateTotalRank+= sr.getRank();
                log.debug("\n"+count+"." + sr.getStockName() + " - " + sr.getMonthTimePeriod()+"-"+ sr.getRank());
                sm.setEndDate(sr.getEndDate());
            }
                sm.setStrategyName("Modified Momentum");
                sm.setTotalRank(calculateTotalRank);
                smList.add(sm);

        }
System.out.println(smList.size());
        List<StockMomentum> highestRank=new ArrayList<>();
        highestRank=smList
                .stream()
                .sorted(Comparator.comparing(StockMomentum::getTotalRank))
                .limit(20)
                .toList();
        highestRank.forEach(stockMomentum -> System.out.println(stockMomentum.getStockName() + " - " + stockMomentum.getTotalRank()));

        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

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

