package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.model.PureStockMomentum;
import com.stock.analysis.Momentum_Strategy.services.PureSMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping("/pureSM")
@Slf4j
public class PureSMController {
    @Autowired
    private PureSMService pureSMService;

    @GetMapping
    public List<PureStockMomentum> getAllStocks() {
        return pureSMService.getAllStocks();
    }

    @GetMapping("/experiment")
    public void stockExperiment(){
        List<PureStockMomentum> getAllStocks=pureSMService.getAllStocks().stream().
                sorted(Comparator.comparing(PureStockMomentum::getEndDate)).toList();
        Map<Date,List<PureStockMomentum>> smByGrpDateMap=new LinkedHashMap<>();


        smByGrpDateMap=   getAllStocks.stream().collect(groupingBy(PureStockMomentum::getEndDate));

        // Printing the map
        for (Map.Entry<Date, List<PureStockMomentum>> entry : smByGrpDateMap.entrySet()) {
            Date endDate = entry.getKey();
            List<PureStockMomentum> momentumList = entry.getValue();
            log.info(STR."End Date: \{endDate}=======================");
            for (PureStockMomentum momentum : momentumList) {
                // Print other details of PureStockMomentum if needed
                log.info(STR."\{momentum.getStockName()}");
            }
        }
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

    @PostMapping("/{startYear}/{endYear}")
    public ResponseEntity<Void> getTopPureMomentumStocks(@PathVariable int startYear,@PathVariable int endYear) {
        pureSMService.getTopPureMomentumStocks(startYear,endYear);
        return ResponseEntity.noContent().build();
    }
}
